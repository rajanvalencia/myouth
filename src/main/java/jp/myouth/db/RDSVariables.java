package jp.myouth.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RDSVariables {

	public String dbVariables(String varName) {
		try (InputStream input = RDSVariables.class.getClassLoader().getResourceAsStream("database.properties")) {

			Properties prop = new Properties();

			if (input == null) {
				System.out.println("Sorry, unable to find database.properties");
				return null;
			}

			// load a properties file from class path, inside static method
			prop.load(input);

			String data = prop.getProperty(varName);

			return data;

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}	
