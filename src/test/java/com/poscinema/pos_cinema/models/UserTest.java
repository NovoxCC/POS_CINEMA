package com.poscinema.pos_cinema.models;

import javafx.application.Platform;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

   /*
    Los errores al ejecutar todas las pruebas, incluso si son correctas, se deben a que se están ejecutando métodos relacionados
    con la interfaz de usuario fuera del hilo de la aplicación de JavaFX.
*/

    @BeforeAll
    public static void setUp() {
        // Inicializar el entorno de JavaFX
        Platform.startup(() -> {});
    }

    @AfterAll
    public static void tearDown() {
        // Limpiar el entorno de JavaFX
        Platform.exit();
    }

    @Test
    public void testLogin_CorrectCredentials_Successful() {
        // Arrange
        String username = "user";
        String correctPassword = "password";

        // Act
        boolean loginResult = User.login(username, correctPassword);

        // Assert
        assertTrue(loginResult, "El inicio de sesión con credenciales correctas debería ser exitoso");
    }

    @Test
    public void testLogin_IncorrectCredentials_Failure() {
        // Arrange
        String username = "user";
        String incorrectPassword = "PASWOR";

        // Act
        boolean loginResult = User.login(username, incorrectPassword);

        // Assert
        assertFalse(loginResult, "El inicio de sesión con credenciales incorrectas debería fallar");
    }

    @Test
    public void testGetRoleId_ExistingUser_ReturnsRoleId() {
        // Arrange
        String existingUsername = "user";

        // Act
        Integer roleId = User.getRoleId(existingUsername);

        // Assert
        assertNotNull(roleId, "Se debería devolver un role ID para un usuario existente");
    }

    @Test
    public void testGetRoleId_NonExistingUser_ReturnsNull() {
        // Arrange
        String nonExistingUsername = "nonExistingUser";

        // Act
        Integer roleId = User.getRoleId(nonExistingUsername);

        // Assert
        assertNull(roleId, "No se debería devolver un role ID para un usuario que no existe");
    }

    @Test
    public void testBalance() {
        // Arrange
        User user = new User();
        int expectedBalance = 100; // Establece el balance esperado

        // Act
        user.setBalance(expectedBalance); // Establece el balance en el usuario

        // Assert
        assertEquals(expectedBalance, user.getBalance()); // Verifica que el balance sea el esperado
    }

    @Test
    public void testShowErrorDialog() {
        // No se puede probar directamente la función showErrorDialog, ya que crea una ventana de alerta que no se puede manipular desde un test unitario.
        // Pero podemos asumir que si no se lanza ninguna excepción, entonces la función está funcionando correctamente.
        assertDoesNotThrow(() -> User.showErrorDialog("Error de prueba", "Mensaje de prueba"));
    }



}
