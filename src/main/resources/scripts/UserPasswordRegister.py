import mysql.connector
from argon2 import PasswordHasher, Type
import sys
import secrets  # Importar el módulo secrets para generar salt aleatorio
from database_config import config  # Importa el diccionario de configuración

# Función para encriptar la contraseña con Argon2 y el salt obtenido
def encriptar_contrasena(contrasena, salt):
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

# Función para insertar un nuevo usuario en la base de datos
def insertar_usuario(usuario, contrasena):
    try:
        conn = mysql.connector.connect(**config)
        cursor = conn.cursor()
        
        # Generar un salt aleatorio
        salt = secrets.token_hex(15)
        
        # Encriptar la contraseña con el salt generado
        contrasena_encriptada = encriptar_contrasena(contrasena, salt)
        
        # Insertar el usuario y su contraseña en la base de datos
        cursor.execute("INSERT INTO Users (username, password_hash, salt) VALUES (%s, %s, %s)", (usuario, contrasena_encriptada, salt))
        conn.commit()
        print("Usuario registrado correctamente.")
    except mysql.connector.Error as err:
        print("Error al registrar el usuario:", err)
    finally:
        cursor.close()
        conn.close()

# Función principal
def main(usuario, contrasena):
    insertar_usuario(usuario, contrasena)

if __name__ == "__main__":
    if len(sys.argv) < 3:
        # Código de error 3: Argumentos insuficientes
        sys.exit(3)
    usuario = sys.argv[1]
    contrasena = sys.argv[2]
    main(usuario, contrasena)
