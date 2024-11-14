package br.com.tads.sistemaBancario.models.dao;

import java.beans.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcConnection {

    protected CallableStatement cStmt;

    protected Connection connection;

    protected ResultSet resultSet;

    public Connection getConnection() {
        try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         String url = "jdbc:mysql://localhost:3306/lpoo2";
         String usuario = "root";
         String senha = "";

         this.connection = DriverManager.getConnection(url,usuario,senha);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
        	e1.printStackTrace();
        }
        return connection;
    }
}