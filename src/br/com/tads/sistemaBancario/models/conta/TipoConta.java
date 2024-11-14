package br.com.tads.sistemaBancario.models.conta;

public enum TipoConta {
	CORRENTE("corrente"),
	INVESTIMENTO("investimento");
	
	private String tipo;
	
	TipoConta(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return this.tipo;
	}
}
