package br.com.tads.sistemaBancario.controllers;


import java.util.List;


import br.com.tads.sistemaBancario.commom.Utility;
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
        	String validacao = validaForm(cliente);
        	if("".equals(validacao)) {
        		Cliente clientExist = clienteDAO.findById(cliente.getCpf());
        		if(clientExist == null) {
        			if(clienteDAO.save(cliente))
        				return new Result(true , "cliente salvo com sucesso");
        		}else {
        			return new Result(false , "cliente com o cpf " +cliente.getCpf()+ "já consta na base de dados");
        		}
        	}else {
        		return new Result(false , validacao);
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return new Result(false , "erro ao salvar cliente");
    }

    public Cliente getCliente(String id) {
    	try {
            return clienteDAO.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return null;
    }

    public List<Cliente> getAllClientes() {
    	try {
            return clienteDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return null;
    }

    public Result updateCliente(Cliente cliente) {
        try {
        	String validacao = validaForm(cliente);
        	if("".equals(validacao)) {
	        	Cliente clientExist = clienteDAO.findById(cliente.getCpf());
	        	if(clientExist != null) {
	        		clienteDAO.update(cliente);
	        	}else {
	        		return new Result(false , "Cliente com o cpf "+cliente.getCpf()+" não encontrado");
	        	}
        	}else {
        		return new Result(false , validacao);
        	}
        } catch (Exception e) {
            e.printStackTrace();
    		return new Result(false , "erro ao salvar cliente");
        }
		return new Result(true , "cliente salvo com sucesso");

    }

    public Result deleteCliente(String id) {
        try {
            clienteDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
    		return new Result(false , "erro ao deletar cliente");
        }
		return new Result(true , "cliente removido com sucesso");

    }
    
    private String validaForm(Cliente cliente) {
    	if("".equals(cliente.getNome()) || cliente.getNome() == null) {
    		return "preencha o campo Nome";
    	}
    	if("".equals(cliente.getSobrenome()) || cliente.getSobrenome() == null) {
    		return "preencha o campo Sobrenome";
    	}
    	if("".equals(cliente.getCpf()) || cliente.getCpf() == null) {
    		return "preencha o campo Cpf";
    	}
    	if("".equals(cliente.getRg()) || cliente.getRg() == null) {
    		return "preencha o campo Rg";
    	}
    	
    	if("".equals(cliente.getEndereco()) || cliente.getEndereco() == null) {
    		return "preencha o campo Endereço";
    	}
    	if(!Utility.validarCpf(cliente.getCpf())) {
    		return "Cpf inválido";
    	}
    	return "";
    }
}
