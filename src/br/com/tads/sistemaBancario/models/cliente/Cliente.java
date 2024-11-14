package br.com.tads.sistemaBancario.models.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.tads.sistemaBancario.models.conta.Conta;
import br.com.tads.sistemaBancario.models.conta.ContaCorrente;
import br.com.tads.sistemaBancario.models.conta.ContaInvestimento;

public class Cliente {
    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String endereco;
    private Conta conta;
    public Cliente(String nome, String sobrenome, String rg, String cpf, String endereco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public Cliente() {
    }

    public Cliente(String cpf) {
    	this.cpf = cpf;
    }

    @Override
    public String toString() {
        return cpf;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}


	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Cliente))
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
}

