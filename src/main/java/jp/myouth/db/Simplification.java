package jp.myouth.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Simplification {
	private Connection conn = null;
	private Statement stmt = null;

	RDSVariables var = new RDSVariables();

	private final String hostname = var.dbVariables("HOSTNAME");
	private final String dbname = var.dbVariables("DATABASE.NAME");
	private final String username = var.dbVariables("USERNAME");
	private final String password = var.dbVariables("PASSWORD");
	private final String port = var.dbVariables("PORT");

	public void open() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user="
					+ username + "&password=" + password);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null || stmt != null)
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void simplifyCompany() throws IOException {
		ArrayList<String> company_data = new ArrayList<String>();
		ArrayList<String> career_data = new ArrayList<String>();
		try {

			career_data.add("çÇçZ");
			career_data.add("ëÂäw");
			career_data.add("äwâ@");

			String query = "SELECT company FROM participants";
			ResultSet rset = stmt.executeQuery(query);

			while (rset.next())
				company_data.add(rset.getString("company"));

			String query1 = "SELECT * FROM participants WHERE company = ?";
			PreparedStatement stmt = conn.prepareStatement(query1);

			String update = "UPDATE participants SET company = ? WHERE company = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update);

			for (String company : company_data) {
				for (String career : career_data) {
					stmt.setString(1, company+career);
					ResultSet rset1 = stmt.executeQuery();
					if (rset1.next()) {
						stmt1.setString(1, company + career);
						stmt1.setString(2, company);
						stmt1.executeUpdate();
					}
					rset1.close();
				}
			}
			
			rset.close();
			stmt.close();
			stmt1.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
