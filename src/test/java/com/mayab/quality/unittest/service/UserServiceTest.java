package com.mayab.quality.unittest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mayab.quality.loginunittest.dao.IDAOUser;
import com.mayab.quality.loginunittest.model.User;
import com.mayab.quality.loginunittest.service.UserService;

class UserServiceTest {

    private UserService service;
    private IDAOUser dao;
    private User user;
    private HashMap<Integer, User> db;

    @BeforeEach
    public void setUp() throws Exception{
        dao = mock(IDAOUser.class);
        service = new UserService(dao);
        user = mock(User.class);
        db = new HashMap<Integer, User>();
    }

    @Test
    public void whenPasswordShort_test() {
        String shortPass = "123";
        String name = "user1";
        String email = "user@gmail.com";
        User user = null;
        
        when(dao.findUserByEmail(anyString())).thenReturn(user);
        when(dao.save(any(User.class))).thenReturn(1);
        
        User result = service.createUser(name, email, shortPass);
        
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenPasswordLong_test() {
        String longPass = "thispasswordistoolong";  
        String name = "user1";
        String email = "user@gmail.com";
        User user = null;

        when(dao.findUserByEmail(anyString())).thenReturn(user);

        User result = service.createUser(name, email, longPass);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenUserUpdateData_test() {
        User oldUser = new User("oldUser", "oldEmail", "oldPassword");
        db.put(1, oldUser);
        oldUser.setId(1);
        
        User newUser = new User("newUser", "oldEmail", "newPassword");
        newUser.setId(1);
        
        when(dao.findById(1)).thenReturn(oldUser);
        
        when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) throws Throwable{
                User arg = (User) invocation.getArguments()[0];
                db.replace(arg.getId(), arg);
                
                return db.get(arg.getId());
            }
        });
        
        // Exercise
        User result = service.updateuser(newUser);
        
        // Verification
        assertThat(result.getName(), is("newUser"));
        assertThat(result.getPassword(), is("newUser"));
    }

    @Test
    public void createUser_HappyPath() {
        String name = "user1";
        String email = "user@gmail.com";
        String password = "password123";

        when(dao.findUserByEmail(email)).thenReturn(null);
        when(dao.save(any(User.class))).thenReturn(1);

        User result = service.createUser(name, email, password);

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), is(email));
    }

    @Test
    public void createUser_DuplicateEmail() {
        String name = "user1";
        String email = "user@gmail.com";
        String password = "password123";
        User existingUser = new User("existingUser", email, password);
    
        when(dao.findUserByEmail(email)).thenReturn(existingUser);
    
        User result = service.createUser(name, email, password);
      
        assertThat(result, is(nullValue()));
     
        verify(dao, never()).save(any(User.class));
    }


    @Test
    public void deleteUser_HappyPath() {
        int userId = 1;
        
        // Verificar que el usuario existe
        User existingUser = new User("user1", "user@gmail.com", "password123");
        existingUser.setId(userId);
        
        when(dao.findById(userId)).thenReturn(existingUser);  // Simulamos que el usuario existe

        // Simulamos que la eliminación es exitosa
        when(dao.deleteById(userId)).thenReturn(true);

        boolean result = service.deleteUser(userId);

        // Verificación
        assertThat(result, is(true));
    }

    @Test
    public void findUserByEmail_HappyPath() {
        String email = "user@gmail.com";
        User expectedUser = new User("user1", email, "password123");

        when(dao.findUserByEmail(email)).thenReturn(expectedUser);

        User result = service.findUserByEmail(email);

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), is(email));
    }

    @Test
    public void findUserByEmail_NotFound() {
        String email = "nonexistent@gmail.com";

        when(dao.findUserByEmail(email)).thenReturn(null);

        User result = service.findUserByEmail(email);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void findAllUsers_HappyPath() {
        List<User> users = Arrays.asList(new User("user1", "email1@gmail.com", "password1"),
                                         new User("user2", "email2@gmail.com", "password2"));

        when(dao.findAll()).thenReturn(users);

        List<User> result = service.findAllUsers();

        assertThat(result, hasSize(2));
        assertThat(result.get(0).getName(), is("user1"));
        assertThat(result.get(1).getName(), is("user2"));
    }
}

