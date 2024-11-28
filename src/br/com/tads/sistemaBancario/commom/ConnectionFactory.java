package br.com.tads.sistemaBancario.commom;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public static Connection getConnection() throws SQLException ,IOException{
         String url = "jdbc:mysql://localhost:3306/lpoo2";
         String usuario = "root";
         String senha = "root";
         return DriverManager.getConnection(url,usuario,senha);
    }
}
