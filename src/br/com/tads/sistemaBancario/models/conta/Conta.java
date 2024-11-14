package br.com.tads.sistemaBancario.models.conta;

import br.com.tads.sistemaBancario.models.cliente.Cliente;

public abstract class Conta implements ContaModelI{
	protected Cliente dono;
	protected int numero;
    protected double saldo;
	public Conta() {
	}
    @Override
    public boolean deposita(double valor) {
    	if(valor > 0) {
    		saldo += valor;
    		return true;
    	}
        return false;
    }

    @Override
    public boolean saca(double valor) {
        if (valor > 0) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public Cliente getDono() {
        return dono;
    }

    @Override
    public int getNumero() {
        return numero;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }
	
}
