import sys
import traceback
import mysql.connector
from argon2 import PasswordHasher, Type  # Importar el tipo de Argon2
from database_config import config  # Importa el diccionario de configuración

# Función para obtener el salt asociado al usuario desde la base de datos
def obtener_salt(usuario):
    try:
        conn = mysql.connector.connect(**config)
        cursor = conn.cursor()
        cursor.execute("SELECT salt FROM Users WHERE username = %s", (usuario,))
        result = cursor.fetchone()
        if result:
            salt = result[0]
            conn.close()
            return salt
        else:
            print("No se encontró ningún resultado para el usuario:", usuario)
            conn.close()
            return None
    except mysql.connector.Error as err:
        # Código de error 1: Error al obtener el salt
        traceback.print_exc()  # Imprimir detalles de la excepción
        sys.exit(1)

# Función para encriptar la contraseña con Argon2 y el salt obtenido
def encriptar_contrasena(contrasena, salt):
    try:
        ph = PasswordHasher(
            time_cost=3,  # Factor de costo de tiempo
            memory_cost=65536,  # Factor de costo de memoria en KiB
            parallelism=4,  # Número de hilos de procesamiento
            hash_len=32,  # Longitud del hash en bytes
            salt_len=15,  # Longitud del salt en bytes
            type=Type.ID,  # Tipo de Argon2 (Argon2id)
        )
        salt_bytes = salt.encode('utf-8')  # Codificar el salt como bytes
        return ph.hash(contrasena, salt=salt_bytes)  # Pasar el salt como bytes
    except Exception as e:
        traceback.print_exc()  # Imprimir detalles de la excepción
        sys.exit(1)

# Función principal para encriptar la contraseña
def main(usuario, contrasena):
    try:
        salt = obtener_salt(usuario)
        print(salt)
        if salt:
            contrasena_encriptada = encriptar_contrasena(contrasena, salt)
            sys.stdout.write(contrasena_encriptada)
            sys.stdout.flush() # Asegurarse de que la salida se envíe inmediatamente
        else:
            # Código de error 2: Salt no encontrado para el usuario
            sys.exit(2)
    except Exception as e:
        traceback.print_exc()  # Imprimir detalles de la excepción
        sys.exit(1)

if __name__ == "__main__":
    try:
        if len(sys.argv) < 3:
            # Código de error 3: Argumentos insuficientes
            sys.exit(3)
        usuario = sys.argv[1]
        contrasena = sys.argv[2]
        main(usuario, contrasena)
    except Exception as e:
        traceback.print_exc()  # Imprimir detalles de la excepción
        sys.exit(1)
