package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

public class Server {

	public static void main(String[] args) {
		System.out.println("Hello, world!");
		Gson g;
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost/__db__?user=root&password=root");
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM *;");
		} catch (SQLException e) {}
	}

}
