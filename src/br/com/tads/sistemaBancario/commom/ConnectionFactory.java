package br.com.tads.sistemaBancario.commom;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	public static Connection getConnection() throws Exception{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/lpoo2";
			String usuario = "root";
			String senha = "root";
			return DriverManager.getConnection(url,usuario,senha);
    }
}
