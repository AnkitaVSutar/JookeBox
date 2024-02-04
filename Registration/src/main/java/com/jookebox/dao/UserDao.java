package com.jookebox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.jookebox.exception.EmailValidation;
import com.jookebox.model.User;

public class UserDao {

	public int register(User user) throws ClassNotFoundException, EmailValidation {
		String INSERT_USERS_SQL = "INSERT INTO User"
				+ "  (id, first_name, last_name, email, password, address, contact) VALUES "
				+ " (?, ?, ?, ?, ?,?,?);";

		int result = 0;

		try {
			Connection connection = getConnection();

			PreparedStatement userNamePrepStmt = connection
					.prepareStatement("select * from user where email=?");
			userNamePrepStmt.setString(1, user.getEmail());
			ResultSet rs = userNamePrepStmt.executeQuery();
			if (rs.next()) {
                throw new EmailValidation("Email already Registered.");
			} else {
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
				UUID id = UUID.randomUUID();
				preparedStatement.setString(1, id.toString());
				preparedStatement.setString(2, user.getFirstName());
				preparedStatement.setString(3, user.getLastName());
				preparedStatement.setString(4, user.getEmail());
				preparedStatement.setString(5, user.getPassword());
				preparedStatement.setString(6, user.getAddress());
				preparedStatement.setString(7, user.getContact());

				System.out.println(preparedStatement);
				// Step 3: Execute the query or update query
				result = preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return result;
	}

	public boolean validateUser(String email, String password) {
		boolean st = false;
		try {

			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("select * from user where email=? and password=?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			st = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useSSL=false", "root", "root");
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

}
