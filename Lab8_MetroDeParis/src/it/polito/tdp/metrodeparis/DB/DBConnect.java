package it.polito.tdp.metrodeparis.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect
{
	private static String jdbcURL = "jdbc:mysql://localhost/metroparis?user=root&password=root";
	private static Connection conn;
	
	public static Connection getConnection()
	{	
		try 
		{	
			conn = DriverManager.getConnection(jdbcURL);
			return conn;
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
