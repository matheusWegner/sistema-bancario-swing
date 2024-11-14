package br.com.tads.sistemaBancario.models.conta;

import br.com.tads.sistemaBancario.models.cliente.Cliente;

public interface ContaModelI {
	boolean deposita(double valor);
    boolean saca(double valor);
    Cliente getDono();
    int getNumero();
    double getSaldo();
    void remunera();
}
