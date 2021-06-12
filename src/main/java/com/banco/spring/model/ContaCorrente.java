package com.banco.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ContaCorrente {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroAgencia; //vai ser usado só quando for guardar o dado da conta corente
    private String numeroContaCorrente;
    private double saldoContaCorrente;

    //private List<Extrato> extrato = new ArrayList<>();
    
    public ContaCorrente() {

    }

    public ContaCorrente(Long id, String numeroContaCorrente, double saldoContaCorrente) {
        this.id = id;
        this.numeroContaCorrente = numeroContaCorrente;
        this.saldoContaCorrente = saldoContaCorrente;
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
