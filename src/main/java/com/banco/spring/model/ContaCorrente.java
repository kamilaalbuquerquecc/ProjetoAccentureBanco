package com.banco.spring.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ContaCorrente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroAgencia; // vai ser usado só quando for guardar o dado da conta corente
	private String numeroContaCorrente;
	private double saldoContaCorrente;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "idCliente")
	private Cliente cliente;
	
	@JsonIgnore
    @OneToMany
    @JoinColumn(name="idConta")
    private Set<Extrato> extrato;

	public ContaCorrente() {

	}

	public ContaCorrente(Long id, String numeroContaCorrente, double saldoContaCorrente) {
		this.id = id;
		this.numeroContaCorrente = numeroContaCorrente;
		this.saldoContaCorrente = saldoContaCorrente;
	}
	public void saque(float valor) {
		if (valor < this.saldoContaCorrente) {
			this.saldoContaCorrente -= valor;
		}
	}

	public void deposito(double valor) {
	    this.saldoContaCorrente += valor;
    }

    public void transferencia(ContaCorrente conta, double valor) {
        if (valor < this.saldoContaCorrente) {
        	this.saldoContaCorrente -= valor;
        	conta.deposito(valor);
		}
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public String getNumeroContaCorrente() {
		return numeroContaCorrente;
	}

	public void setNumeroContaCorrente(String numeroContaCorrente) {
		this.numeroContaCorrente = numeroContaCorrente;
	}

	public double getSaldoContaCorrente() {
		return saldoContaCorrente;
	}

	public void setSaldoContaCorrente(double saldoContaCorrente) {
		this.saldoContaCorrente = saldoContaCorrente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaCorrente other = (ContaCorrente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
