package com.mayab.quality.integration;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;


import com.mayab.quality.loginunittest.dao.UserMysqlDAO;
import com.mayab.quality.loginunittest.model.User;
import com.mayab.quality.loginunittest.service.UserService;

public class UserServiceTest extends DBTestCase {

    UserService userService;
    UserMysqlDAO userDao;

    public UserServiceTest() {
    	System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,"com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,"jdbc:mysql://localhost:3306/calidad2024");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,"root");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,"123456");	
    }

    @BeforeEach
    public void setUp() throws Exception {
        userDao = new UserMysqlDAO();
        userService = new UserService(userDao);

        IDatabaseConnection connection = getConnection();
	connection.getConnection().setCatalog("calidad2024");
        try {
        	DatabaseOperation.TRUNCATE_TABLE.execute(connection,getDataSet());
            DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
        } catch (Exception e) {
            fail("Error in setup: " + e.getMessage());
        } finally {
            connection.close();
        }
    }

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/resources/initDB.xml"));
    }

    @Test
    public void testCreateUser_HappyPath() {
        // Create a new user with correct data
        User user = userService.createUser("newUser", "newuser@example.com", "validpassword123");
        assertThat(user.getName(), is("newUser"));
        assertThat(user.getEmail(), is("newuser@example.com"));
    }

    @Test
    public void testCreateUser_EmailAlreadyExists() {
        // Create a user with a pre-existing email
        userService.createUser("existingUser", "existing@example.com", "validpassword123");
        User duplicateUser = userService.createUser("newUser", "existing@example.com", "validpassword123");
        assertThat(duplicateUser, is(nullValue())); // Should return null because email is duplicated
    }

    @Test
    public void testCreateUser_ShortPassword() {
        // Try creating a user with a short password
        User user = userService.createUser("shortPasswordUser", "shortpassword@example.com", "short");
        assertThat(user, is(nullValue())); // Should return null because the password is too short
    }

    @Test
    public void testCreateUser_LongPassword() {
        // Try creating a user with a long password
        User user = userService.createUser("longPasswordUser", "longpassword@example.com", "averylongpassword12345");
        assertThat(user, is(nullValue())); // Should return null because the password is too long
    }

    @Test
    public void testUpdateUser() {
        // Create a user to update
        User userToUpdate = userService.createUser("existingUser", "existing@example.com", "validpassword123");
        userToUpdate.setName("updatedName");
        userToUpdate.setPassword("newpassword123");

        // Update user and verify
        User updatedUser = userService.updateuser(userToUpdate);
        assertThat(updatedUser.getName(), is("updatedName"));
        assertThat(updatedUser.getPassword(), is("newpassword123"));
    }

    @Test
    public void testDeleteUser() {
        // Create a user and delete it
        User userToDelete = userService.createUser("deleteUser", "delete@example.com", "password123");
        boolean isDeleted = userService.deleteUser(userToDelete.getId());
        assertThat(isDeleted, is(true));

        // Verify that the user no longer exists in the DB
        User deletedUser = userService.findUserById(userToDelete.getId());
        assertThat(deletedUser, is(nullValue())); // Should be null after deletion
    }

    @Test
    public void testFindAllUsers() {
        try {
            // Database connection
            IDatabaseConnection conn = getConnection();
            conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);

            // Insert data for findAllUsers test from the XML
            DatabaseOperation.CLEAN_INSERT.execute(conn, new FlatXmlDataSetBuilder().build(new File("src/resources/create.xml")));

            // Execute the operation to find all users
            List<User> users = userService.findAllUsers();

            // Verify that the users list is not null and contains at least one user
            assertNotNull("The user list should not be null", users);
            assertThat("The user list should contain at least one user", users.size(), is(greaterThan(0)));

        } catch (Exception e) {
            fail("Error in testFindAllUsers: " + e.getMessage());
        }
    }

    @Test
    public void testFindUserByEmail() {
        try {
            // Database connection
            IDatabaseConnection conn = getConnection();
            conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);

            // Insert data for findUserByEmail test from the XML
            DatabaseOperation.CLEAN_INSERT.execute(conn, new FlatXmlDataSetBuilder().build(new File("src/resources/create.xml")));

            // Execute the operation to find the user by email
            User user = userService.findUserByEmail("finduser@example.com");

            // Verify that the user is not null
            assertNotNull("The user should exist", user);
            assertThat(user.getEmail(), is("finduser@example.com"));

        } catch (Exception e) {
            fail("Error in testFindUserByEmail: " + e.getMessage());
        }
    }

  
    @Test
    public void testFindUserById() {
        try {
            // Database connection
            IDatabaseConnection conn = getConnection();
            conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);

            // Insert data for findUserById test from the XML
            DatabaseOperation.CLEAN_INSERT.execute(conn, new FlatXmlDataSetBuilder().build(new File("src/resources/create.xml")));

            // Execute the operation to find the user by ID
            User user = userService.findUserById(3);

            // Verify that the user is not null
            assertNotNull("The user should exist", user);
            assertThat(user.getId(), is(3));

        } catch (Exception e) {
            fail("Error in testFindUserById: " + e.getMessage());
        }
    }





}
