package br.com.tads.sistemaBancario.controllers;


import java.sql.SQLException;
import java.util.List;

import br.com.tads.sistemaBancario.models.Result;
import br.com.tads.sistemaBancario.models.cliente.Cliente;
import br.com.tads.sistemaBancario.models.dao.ClienteDAO;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public Result addCliente(Cliente cliente) {
        try {
        	Cliente clientExist = clienteDAO.findById(cliente.getCpf());
        	if(clientExist == null) {
        		if(clienteDAO.save(cliente))
        			return new Result(true , "cliente salvo com sucesso");
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return new Result(false , "erro ao salvar cliente");
    }

    public Cliente getCliente(String id) {
    	try {
            return clienteDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return null;
    }

    public List<Cliente> getAllClientes() {
    	try {
            return clienteDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return null;
    }

    public Result updateCliente(Cliente cliente) {
        try {
        	Cliente clientExist = clienteDAO.findById(cliente.getCpf());
        	if(clientExist != null) {
        		clienteDAO.update(cliente);
        	}else {
        		return new Result(false , "Cliente com o cpf "+cliente.getCpf()+" não encontrado");
        	}
        } catch (SQLException e) {
            e.printStackTrace();
    		return new Result(false , "erro ao salvar cliente");
        }
		return new Result(true , "cliente salvo com sucesso");

    }

    public Result deleteCliente(String id) {
        try {
            clienteDAO.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
    		return new Result(false , "erro ao deletera cliente");
        }
		return new Result(true , "cliente removido com sucesso");

    }
}
