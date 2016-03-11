package data;

import java.io.*;
import java.sql.*;
import java.util.*;

import models.LogicalModel;

public abstract class DBModel {
	protected Connection dbConnection = null;
	protected Statement dbStatement = null;
	protected ResultSet rs = null;
	protected List<String> columnNames = new ArrayList<String>();
	protected String type;

	public DBModel(String type) {
		this.type = type;
		switch (type) {
		case "books":
			columnNames.add("book_id");
			columnNames.add("title");
			columnNames.add("author_last_name");
			columnNames.add("author_first_name");
			columnNames.add("rating");
			break;
		case "patrons":
			columnNames.add("last_name");
			columnNames.add("first_name");
			columnNames.add("street_address");
			columnNames.add("city");
			columnNames.add("state");
			columnNames.add("zip");
			break;
		case "transactions":
			columnNames.add("patron_id");
			columnNames.add("book_id");
			columnNames.add("transaction_date");
			columnNames.add("transaction_type");
			break;

		default:
			break;
		}
		try {
			// Load the properties file
			Properties props = new Properties();
			props.load(new FileInputStream("db.properties"));

			// Read the props
			String theUser = props.getProperty("user");
			String thePassword = props.getProperty("password");
			String theDburl = props.getProperty("dburl");

			dbConnection = DriverManager.getConnection(theDburl, theUser, thePassword);

			// Create the statement
			dbStatement = dbConnection.createStatement();

			rs = dbStatement.executeQuery("select * from " + type);

		} catch (FileNotFoundException e) {
			System.out.println("Properties file not found. " + "Check README for instructions.");
			e.printStackTrace();
			System.out.println("\n\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\n\n");
		} catch (SQLException e) {
			System.out.println("Unable to connect to database. " + "Check README for instructions.");
			e.printStackTrace();
			System.out.println("\n\n");
		}
	}

	public void add(LogicalModel obj) throws SQLException {
		String columns = "";
		ListIterator<String> it = columnNames.listIterator();
		while (it.hasNext()) {
			columns = columns + it.next();
			columns = columns + (it.hasNext() ? "," : "");
		}
		String values = "";
		ListIterator<String> it2 = obj.getValues().listIterator();
		while (it2.hasNext()) {
			values = values + "'" + it2.next() + "'";
			values = values + (it2.hasNext() ? "," : "");
		}
		String query = String.format("insert into %s (%s) values (%s)", 
									 this.type, columns, values);

		try {
			dbStatement.executeUpdate(query);
			System.out.format("Successfully added to %s.\n", this.type);
		} catch (Exception e) {
			System.out.println("Failed to add Book");
			e.printStackTrace();
			System.out.println("\n\n");
		}

		rs = dbStatement.executeQuery("select * from " + type);
	}

	protected String columnName(int index) {
		return columnNames.get(index);
	}

	public void close() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (dbStatement != null) {
			dbStatement.close();
		}
		if (dbConnection != null) {
			dbConnection.close();
		}
	}
}