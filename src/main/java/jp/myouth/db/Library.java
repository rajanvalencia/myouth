package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Library {
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

	public ArrayList<String> getCountryNames(String lang) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT name FROM lists_countries WHERE lang = ? ORDER BY CAST(name AS CHAR)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, lang);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public ArrayList<String> getAllPrefectureCodes() {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT prefecture_code FROM lists_prefectures";
			ResultSet rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				data.add(rset.getString("prefecture_code"));
			}
			rset.close();
			
			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPrefectureName(String code) {
		try {
			String query = "SELECT prefecture_name FROM lists_prefectures WHERE prefecture_code = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, code);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return rset.getString("prefecture_name");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCountryCode(String name, String lang) {
		try {
			String query = "SELECT code FROM lists_countries WHERE name = ? AND lang = ? ";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, lang);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) {
				return rset.getString("code");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getCountriesLanguages(){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT lang FROM lists_countries GROUP BY lang";
			ResultSet rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				data.add(rset.getString("lang"));
			}
			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Library lib = new Library();
		lib.open();
		ArrayList<String> codes = lib.getAllPrefectureCodes();
		
		for(String code : codes) {
			System.out.println(code+": "+lib.getPrefectureName(code));
		}
		lib.close();
	}

}
