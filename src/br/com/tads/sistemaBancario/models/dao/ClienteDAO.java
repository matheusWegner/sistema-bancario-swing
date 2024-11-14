package br.com.tads.sistemaBancario.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tads.sistemaBancario.models.cliente.Cliente;
import br.com.tads.sistemaBancario.models.dao.interfaces.GenericDAOI;

public class ClienteDAO  extends JdbcConnection implements GenericDAOI<Cliente , String> {
	 
	private static final String INSERT_CLIENTES_SQL = "INSERT INTO cliente" +
		        "  (nome, sobrenome, rg, cpf, endereco) VALUES " +
		        " (?, ?, ?, ?, ?);";
	private static final String SELECT_CLIENTE_BY_ID = "select nome,sobrenome,rg,cpf,endereco from cliente where cpf =?";
    private static final String SELECT_ALL_CLIENTES = "select * from cliente";
    private static final String DELETE_CLIENTES_SQL = "delete from cliente where cpf = ?;";
    private static final String UPDATE_CLIENTES_SQL = "update cliente set nome = ?, sobrenome= ?, rg =?, endereco =? where cpf = ?;";

	@Override
	public boolean save(Cliente cliente) throws SQLException{
		 try (
		    Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENTES_SQL)) {
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getSobrenome());
            preparedStatement.setString(3, cliente.getRg());
            preparedStatement.setString(4, cliente.getCpf());
            preparedStatement.setString(5, cliente.getEndereco());
            preparedStatement.executeUpdate();
            return true;
	     } catch (SQLException e) {
        	e.printStackTrace();
	     }		
		 return false;
	}

	@Override
	public boolean update(Cliente cliente) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENTES_SQL);) {
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getSobrenome());
            statement.setString(3, cliente.getRg());
            statement.setString(4, cliente.getEndereco());
            statement.setString(5, cliente.getCpf());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

	@Override
	public Cliente findById(String id) throws SQLException{
        Cliente cliente = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENTE_BY_ID);) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");
                String rg = rs.getString("rg");
                String cpf = rs.getString("cpf");
                String endereco = rs.getString("endereco");
                cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setSobrenome(sobrenome);
                cliente.setRg(rg);
                cliente.setCpf(cpf);
                cliente.setEndereco(endereco);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return cliente;
    }

	@Override
	 public List<Cliente> findAll() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENTES);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");
                String rg = rs.getString("rg");
                String cpf = rs.getString("cpf");
                String endereco = rs.getString("endereco");
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setSobrenome(sobrenome);
                cliente.setRg(rg);
                cliente.setCpf(cpf);
                cliente.setEndereco(endereco);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return clientes;
    }

	@Override
	 public boolean delete(String id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(DELETE_CLIENTES_SQL);) {
            statement.setString(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

}
