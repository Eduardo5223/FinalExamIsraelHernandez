package com.mayab.quality.integration;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


import com.mayab.quality.loginunittest.dao.UserMysqlDAO;
import com.mayab.quality.loginunittest.model.User;


public class UserDAOTest extends DBTestCase{

UserMysqlDAO daoMySql;
	
	public UserDAOTest() {
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,"com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,"jdbc:mysql://localhost:3306/calidad2024");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,"root");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,"123456");	
	
	}

	@BeforeEach
	protected void setUp() throws Exception {
	    // Initialize DAO
	    daoMySql = new UserMysqlDAO(); 
	    
	    // Set the initial condition of the database
	    IDatabaseConnection connection = getConnection(); 
	    try {
	        // Debugging: Verifica que la conexión esté bien
	        System.out.println("Conexión establecida correctamente.");
	        
	        DatabaseOperation.TRUNCATE_TABLE.execute(connection, getDataSet());
	        DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
	    } catch(Exception e) {
	        fail("Error in setup: "+ e.getMessage()); 
	    } finally {
	        connection.close(); 
	    }
	}


	 protected IDataSet getDataSet() throws Exception
	    {
	        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/resources/initDB.xml"));
	    }
	 
	 @SuppressWarnings("deprecation")
	@Test
		public void testAgregarUsuario() {
			User users = new User("username2", "correo2@correo.com", "123456");
			
			daoMySql.save(users);
			
			// Verify data in database
			try {
				// This is the full database
				IDatabaseConnection conn = getConnection();
				conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
				IDataSet databaseDataSet = conn.createDataSet(); 
				String[] tablas = databaseDataSet.getTableNames();
				
				ITable actualTable = databaseDataSet.getTable("usuariosFinal");
				
				// Read XML with the expected result
				IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/create.xml"));
				ITable expectedTable = expectedDataSet.getTable("usuariosFinal");
				
				Assertion.assertEquals(expectedTable, actualTable);
				
				
			} catch (Exception e) {
				// TODO: handle exception
				fail("Error in insert test: " + e.getMessage());
			}	
		}
}
