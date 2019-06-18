package jp.myouth.db;

public class RDSVariables {
	
	/*
	 * mysql -h myouthdb.c2hims90pztt.ap-northeast-1.rds.amazonaws.com -P 3306 -u
	 * rajanvalencia -p myouthdb --ssl-ca=\mysql-certs\rds-combined-ca-bundle.pem
	 * --ssl-verify-server-cert
	 */
	
	protected String hostname() {
		return "myouthdb.c2hims90pztt.ap-northeast-1.rds.amazonaws.com";
	}
	
	protected String dbname() {
		return "myouthdb";
	}
	
	protected String username() {
		return "rajanvalencia";
	}
	
	protected String password() {
		return "nLObZqgi";
	}
	
	protected String port() {
		return "3306";
	}
}
