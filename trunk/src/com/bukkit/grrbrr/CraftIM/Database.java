package com.bukkit.grrbrr.CraftIM;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	
	private Connection connection = null;
	
	private final CraftIM plugin;
	private final String DB_NAME = "sqlite.db";
	
	public Database(CraftIM pluginInstance) throws ClassNotFoundException {
		plugin = pluginInstance;
		Class.forName("org.sqlite.JDBC");
		establishConnection();
	}
		
	/**
	 * Establish Database connection
	 * also creates DB file if not exists
	 */
	public void establishConnection() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlite:"
					+ plugin.getDataFolder().getAbsolutePath()
					+ "/" + DB_NAME);
			setConnection(conn);
		} catch (SQLException e) {
			onSqlException(e);
		}
	}
	
	/** 
	 * Close DB connection 
	 */
	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			onSqlException(e);
		}
	}
	
	/**
	 * Get method for member connection
	 * @return Connection
	 */
	private Connection getConnection() {
		return connection;
	}
	
	/**
	 * Set method for member connection.
	 * Should only be called by establishConnection()
	 * @param The new Connection that should be set
	 */
	private void setConnection(Connection newConnection) {
		connection = newConnection;
	}
	
	/**
	 * Triggers if a SqlException occurs in any operation
	 * @param e
	 */
	public void onSqlException(SQLException e) {
		e.printStackTrace();
	}
	
	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, 
	 * or DELETE statement or an SQL statement that returns nothing, such as an 
	 * SQL DDL statement. 
	 * @param The SQL statement
	 */
	public void executeUpdate(String sqlStatement) {
		try {
			if (connection.isClosed()) {
				establishConnection();
			}
			
			Statement stat = connection.createStatement();
			System.out.println(sqlStatement);
			stat.executeUpdate(sqlStatement);
			
		} catch (SQLException e) {
			onSqlException(e);
		}
	}
	
	
}
