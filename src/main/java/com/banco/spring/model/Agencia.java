package com.banco.spring.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Agencia implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idAgencia;
    private String nomeAgencia;
    private String endereco;
    private String telefone;
      
    public Agencia() {
    }
    
    public Agencia(Long idAgencia, String nomeAgencia, String endereco, String telefone) {
        this.idAgencia = idAgencia;
        this.nomeAgencia = nomeAgencia;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    @OneToMany(mappedBy="agencia")
    private Set<Cliente> cliente;
    
    public Long getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(Long idAgencia) {
        this.idAgencia = idAgencia;
    }

    public String getNomeAgencia() {
        return nomeAgencia;
    }

    public void setNomeAgencia(String nomeAgencia) {
        this.nomeAgencia = nomeAgencia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAgencia == null) ? 0 : idAgencia.hashCode());
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
		Agencia other = (Agencia) obj;
		if (idAgencia == null) {
			if (other.idAgencia != null)
				return false;
		} else if (!idAgencia.equals(other.idAgencia))
			return false;
		return true;
	}
    
}
