package br.com.tads.sistemaBancario.models.conta;

import br.com.tads.sistemaBancario.models.cliente.Cliente;

public class ContaInvestimento extends Conta {
    private double depositoMinimo;
    private double montanteMinimo;
    public ContaInvestimento(Cliente dono, double depositoInicial, double depositoMinimo,double montanteMinimo) {
    	this.dono = dono;
        this.depositoMinimo = depositoMinimo;
        this.montanteMinimo = montanteMinimo;
        this.saldo = depositoInicial;
    }

    public ContaInvestimento(Integer numero, String cpf, double saldo, double depositoMinimo,double montanteMinimo) {
    	this.dono = new Cliente(cpf);
    	this.numero = numero;
    	this.depositoMinimo = depositoMinimo;
    	this.montanteMinimo = montanteMinimo;
    	this.saldo = saldo;
    }

    @Override
    public boolean deposita(double valor) {
        if (valor > 0 && valor >= depositoMinimo) {
            saldo += valor;
            return true;
        }
        return false;
    }

    @Override
    public boolean saca(double valor) {
        if (valor > 0 && saldo - valor >= montanteMinimo) {
            saldo -= valor;
            return true;
        }
        return false;
    }
    
    @Override
    public void remunera() {
        saldo *= 1.02; 
    }


	public double getDepositoMinimo() {
		return depositoMinimo;
	}

	public void setDepositoMinimo(double depositoMinimo) {
		this.depositoMinimo = depositoMinimo;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getMontanteMinimo() {
		return montanteMinimo;
	}

	public void setMontanteMinimo(double montanteMinimo) {
		this.montanteMinimo = montanteMinimo;
	}
}

