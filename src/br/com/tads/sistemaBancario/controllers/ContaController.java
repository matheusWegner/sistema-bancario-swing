package br.com.tads.sistemaBancario.controllers;

import br.com.tads.sistemaBancario.models.Result;
import br.com.tads.sistemaBancario.models.cliente.Cliente;
import br.com.tads.sistemaBancario.models.conta.Conta;
import br.com.tads.sistemaBancario.models.conta.ContaCorrente;
import br.com.tads.sistemaBancario.models.conta.ContaInvestimento;
import br.com.tads.sistemaBancario.models.dao.ContaDAO;

public class ContaController {
    private ContaDAO contaDAO;

    public ContaController() {
        this.contaDAO = new ContaDAO();
    }

    public Result criarContaCorrente(Cliente cliente, double depositoInicial, double limite) {
    	try {
	    	Conta conta = contaDAO.findByCpf(cliente.getCpf());
	    	if (conta != null && conta.getDono() != null) {
	            return new Result(false, "O cliente já possui uma conta.");
	        }
	
	        if (depositoInicial < 0 || limite < 0) {
	            return new Result(false, "Depósito inicial e limite devem ser positivos.");
	        }
	
	        ContaCorrente contaCorrente = new ContaCorrente(cliente, depositoInicial, limite);
	        cliente.setConta(contaCorrente);
			if (contaDAO.save(contaCorrente)) {
			    return new Result(true, "Conta corrente criada com sucesso!");
			} else {
			    return new Result(false, "Erro ao salvar a conta corrente.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			cliente.setConta(null);
			e.printStackTrace();
		}
	    return new Result(false, "Erro ao salvar a conta corrente.");
    }

    public Result criarContaInvestimento(Cliente cliente, double depositoInicial, double montanteMinimo, double depositoMinimoInicial) {
    	try {
    		Conta conta = contaDAO.findByCpf(cliente.getCpf());
	
	    	if (conta != null && conta.getDono() != null) {
	            return new Result(false, "O cliente já possui uma conta.");
	        }
	
	        if (depositoInicial < depositoMinimoInicial || depositoInicial < 0 || montanteMinimo < 0 || depositoMinimoInicial < 0) {
	            return new Result(false, "Valores inválidos para depósito inicial, montante mínimo ou depósito mínimo inicial.");
	        }
	
	        ContaInvestimento contaInvestimento = new ContaInvestimento(cliente, depositoInicial, depositoMinimoInicial, montanteMinimo);
	        cliente.setConta(contaInvestimento);
        
			if (contaDAO.save(contaInvestimento)) {
			    return new Result(true, "Conta de investimento criada com sucesso!");
			} else {
			    return new Result(false, "Erro ao salvar a conta de investimento.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			cliente.setConta(null);
			e.printStackTrace();
		}
	    return new Result(false, "Erro ao salvar a conta corrente.");

    }
    
    public Result remunerarConta(Cliente cliente) {
        try {
    		Conta conta = contaDAO.findByCpf(cliente.getCpf());
    		conta.remunera();
            if (conta instanceof ContaCorrente) {
            	contaDAO.update((ContaCorrente) conta);
            } else if (conta instanceof ContaInvestimento) {
            	contaDAO.update((ContaInvestimento) conta);
            }
            return new Result(true, "Remuneração efetuada, novo saldo: " + conta.getSaldo());
        } catch (Exception e) {
            return new Result(false, "Ocorreu um erro inesperado ao remunerar, tente novamente mais tarde.");
        }
    }

    public Result depositarConta(Cliente cliente, double valor) {
        try {
    		Conta conta = contaDAO.findByCpf(cliente.getCpf());
            if (conta.deposita(valor)) {
                if (conta instanceof ContaCorrente) {
                	contaDAO.update((ContaCorrente) conta);
                } else if (conta instanceof ContaInvestimento) {
                	contaDAO.update((ContaInvestimento) conta);
                }
                return new Result(true, "Depósito efetuado, novo saldo: " + conta.getSaldo());
            } else {
                return new Result(false, "O valor de depósito é inválido.");
            }
        } catch (Exception e) {
            return new Result(false, "Ocorreu um erro inesperado ao depositar, tente novamente mais tarde.");
        }
    }

    public Result sacarConta(Cliente cliente, double valor) {
        try {
    		Conta conta = contaDAO.findByCpf(cliente.getCpf());
            if (conta != null) {
            	if(conta.saca(valor)) {
            		if (conta instanceof ContaCorrente) {
            			contaDAO.update((ContaCorrente) conta);
            		} else if (conta instanceof ContaInvestimento) {
            			contaDAO.update((ContaInvestimento) conta);
            		}
            		return new Result(true, "Saque efetuado, novo saldo: " + conta.getSaldo());
            	}else {
            		return new Result(false, "valor indisponível para saque , o seu saldo atual é de : "+conta.getSaldo()+" .");
            	}
            } else {
                return new Result(false, "Cliente ainda não possui uma conta.");
            }
        } catch (Exception e) {
            return new Result(false, "Ocorreu um erro inesperado ao sacar, tente novamente mais tarde.");
        }
    }

    public Result verSaldoConta(Cliente cliente) {
    	
    	try {
    		Conta conta = contaDAO.findByCpf(cliente.getCpf());
    		if(conta != null) {
    			return new Result(true, "Saldo da conta atual é: " + conta.getSaldo());
    		}else {
    			return new Result(false, "O Cliente ainda não possui conta vinculada");
    		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return new Result(true, "Erro ao buscar saldo");
    }
}
