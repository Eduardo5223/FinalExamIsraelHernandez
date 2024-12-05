package com.mayab.quality.unittest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import com.mayab.quality.loginunittest.dao.UserMysqlDAO;
import com.mayab.quality.loginunittest.model.User;


public class App 
{
    public static void main( String[] args )
    {
    	try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	    String dbURL = "jdbc:mysql://localhost:3307/calidad2024";
    	    System.out.println("jdbcurl=" + dbURL);
    	    String strUserID = "root";
    	    String strPassword = "123456";
    	    Connection con = DriverManager.getConnection(dbURL, strUserID, strPassword);
    	    System.out.println("Connected to the database.");
    	     Statement stmt = con.createStatement();
    	    System.out.println("Executing query");
    	    ResultSet rs = stmt.executeQuery("SELECT * FROM usuariosFinal");
    	    while (rs.next()) {
    	        System.out.println(rs.getInt(1));
    	     }
    	    con.close();
    	} catch (Exception e) {
    	    System.out.println(e);
    	} finally {
    	 //   con.close();
    	}

    	UserMysqlDAO dao = new UserMysqlDAO();
    	dao.save(new User("name", "email@email", "123456"));
    }
}
