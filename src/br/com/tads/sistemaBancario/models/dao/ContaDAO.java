package br.com.tads.sistemaBancario.models.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tads.sistemaBancario.commom.ConnectionFactory;
import br.com.tads.sistemaBancario.models.conta.Conta;
import br.com.tads.sistemaBancario.models.conta.ContaCorrente;
import br.com.tads.sistemaBancario.models.conta.ContaInvestimento;
import br.com.tads.sistemaBancario.models.dao.interfaces.GenericDAOI;

public class ContaDAO implements GenericDAOI<Conta, Integer> {

    private static final String INSERT_CONTA_SQL = "INSERT INTO conta (numero,tipo,cpf, saldo, limite, deposito_minimo, montante_minimo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CONTA_SQL = "UPDATE conta SET saldo = ?, limite = ?, deposito_minimo = ?, montante_minimo = ? WHERE numero = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM conta WHERE numero = ?";
    private static final String FIND_BY_CPF_SQL = "SELECT * FROM conta WHERE cpf = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM conta";
    private static final String DELETE_CONTA_SQL = "DELETE FROM conta WHERE numero = ?";

    @Override
    public boolean save(Conta conta) throws SQLException, IOException {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTA_SQL)) {
            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setString(2, conta instanceof ContaCorrente ? "C" : "I");
            preparedStatement.setString(3, conta.getDono().getCpf());
            preparedStatement.setDouble(4, conta.getSaldo());
            preparedStatement.setDouble(5, conta instanceof ContaCorrente ? ((ContaCorrente) conta).getLimite() : 0);
            preparedStatement.setDouble(6, conta instanceof ContaInvestimento ? ((ContaInvestimento) conta).getMontanteMinimo() : 0);
            preparedStatement.setDouble(7, conta instanceof ContaInvestimento ? ((ContaInvestimento) conta).getDepositoMinimo() : 0);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean update(Conta conta) throws SQLException, IOException {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTA_SQL)) {
            preparedStatement.setDouble(1, conta.getSaldo());
            preparedStatement.setDouble(2, conta instanceof ContaCorrente ? ((ContaCorrente) conta).getLimite() : 0);
            preparedStatement.setDouble(3, conta instanceof ContaInvestimento ? ((ContaInvestimento) conta).getDepositoMinimo() : 0);
            preparedStatement.setDouble(4, conta instanceof ContaInvestimento ? ((ContaInvestimento) conta).getMontanteMinimo() : 0);
            preparedStatement.setInt(5, conta.getNumero());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Conta findById(Integer id) throws SQLException, IOException {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String tipo = resultSet.getString("tipo");
                String cpf = resultSet.getString("cpf");
                double saldo = resultSet.getDouble("saldo");
                if (tipo.equals("C")) {
                    double limite = resultSet.getDouble("limite");
                    return new ContaCorrente(id, cpf, saldo, limite);
                } else if (tipo.equals("I")) {
                    double depositoMinimo = resultSet.getDouble("deposito_minimo");
                    double montanteMinimo = resultSet.getDouble("montante_minimo");
                    return new ContaInvestimento(id, cpf, saldo, depositoMinimo, montanteMinimo);
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Conta findByCpf(String idCliente) throws SQLException, IOException {
    	try (Connection connection = ConnectionFactory.getConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CPF_SQL)) {
    		preparedStatement.setString(1, idCliente);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if (resultSet.next()) {
    			Integer id = resultSet.getInt("numero");
    			String tipo = resultSet.getString("tipo");
    			String cpf = resultSet.getString("cpf");
    			double saldo = resultSet.getDouble("saldo");
    			if (tipo.equals("C")) {
    				double limite = resultSet.getDouble("limite");
    				return new ContaCorrente(id, cpf, saldo, limite);
    			} else if (tipo.equals("I")) {
    				double depositoMinimo = resultSet.getDouble("deposito_minimo");
    				double montanteMinimo = resultSet.getDouble("montante_minimo");
    				return new ContaInvestimento(id, cpf, saldo, depositoMinimo, montanteMinimo);
    			}
    		}
    		return null;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		throw e;
    	}
    }

    @Override
    public List<Conta> findAll() throws SQLException, IOException {
        List<Conta> contas = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int numero = resultSet.getInt("numero");
                String tipo = resultSet.getString("tipo");
                String cpf = resultSet.getString("cpf");
                double saldo = resultSet.getDouble("saldo");
                if (tipo.equals("C")) {
                    double limite = resultSet.getDouble("limite");
                    contas.add(new ContaCorrente(numero, cpf, saldo, limite));
                } else if (tipo.equals("I")) {
                    double depositoMinimo = resultSet.getDouble("deposito_minimo");
                    double montanteMinimo = resultSet.getDouble("montante_minimo");
                    contas.add(new ContaInvestimento(numero, cpf, saldo, depositoMinimo, montanteMinimo));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return contas;
    }

    @Override
    public boolean delete(Integer id) throws SQLException, IOException {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTA_SQL)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
