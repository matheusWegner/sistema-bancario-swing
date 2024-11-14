package br.com.tads.sistemaBancario.models.conta;

import br.com.tads.sistemaBancario.models.cliente.Cliente;

public class ContaCorrente extends Conta {
    private double limite;

    public ContaCorrente(Cliente dono, double depositoInicial, double limite) {
        this.dono = dono;
        this.limite = limite;
        super.saldo = depositoInicial;
    }

    public ContaCorrente(Integer id , String cpf,double saldo, double limite) {
    	this.dono = new Cliente(cpf);
    	this.limite = limite;
    	super.numero = id;
    	super.saldo = saldo;
    }


    @Override
    public boolean saca(double valor) {
        if (valor > 0 && (saldo - valor >= -limite)) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public void remunera() {
        saldo *= 1.01; 
    }


	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public void setDono(Cliente dono) {
		this.dono = dono;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
    
    
}

