package com.mayab.quality.logginunittest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mayab.quality.loginunittest.dao.IDAOUser;
import com.mayab.quality.loginunittest.model.User;
import com.mayab.quality.loginunittest.service.LoginService;

class LoginServiceTest {

    // Atributos
    private LoginService loginService;
    private IDAOUser daoMock;
    private User userMock;

    @BeforeEach
    void setUp() throws Exception {
        // Crear mock para el DAO y para el User
        daoMock = mock(IDAOUser.class);
        userMock = mock(User.class);
        
        // Instanciar la clase LoginService con el mock del DAO
        loginService = new LoginService(daoMock);
    }

    @Test
    void testLoginSuccess() {
        // Definir comportamiento para el mock del User y del DAO
        when(daoMock.findByUserName("test@example.com")).thenReturn(userMock);
        when(userMock.getPassword()).thenReturn("password123");

        // Ejecutar el método de login
        boolean result = loginService.login("test@example.com", "password123");

        // Verificar que el login fue exitoso
        assertTrue(result);
        verify(userMock).setLogged(true); // Verificar que el usuario fue marcado como logueado
    }

    @Test
    void testLoginFailWrongPassword() {
        // Definir comportamiento para el mock del User y del DAO
        when(daoMock.findByUserName("test@example.com")).thenReturn(userMock);
        when(userMock.getPassword()).thenReturn("wrongpassword");

        //  	Ejecutar el método de login
        boolean result = loginService.login("test@example.com", "password123");
        
        // Verificar que el login falló
        assertFalse(result);
        verify(userMock, never()).setLogged(true); // Verificar que el usuario no fue marcado como logueado
    }

    @Test
    void testLoginUserNotFound() {
        // Definir que el DAO devuelve null (usuario no encontrado)
        when(daoMock.findByUserName("notfound@example.com")).thenReturn(null);

        // Ejecutar el método de login
        boolean result = loginService.login("notfound@example.com", "password123");

        // Verificar que el login falló
        assertFalse(result);
    }
}
