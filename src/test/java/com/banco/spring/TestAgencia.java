package com.banco.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.spring.model.Agencia;
import com.banco.spring.repository.AgenciaRepository;

@SpringBootTest
public class TestAgencia {
	@Autowired
	private AgenciaRepository _agenciaRepository;
	
	@Test
	void criarAgencia() {
		
		Long idAgencia = 1L;
		String nomeAgencia = "Ag 01";
		String endereco = "Rua n 01";
		String telefone = "83987654321";
		
		Agencia criado = new Agencia(idAgencia,nomeAgencia,endereco,telefone);
		Agencia esperado = new Agencia(1L,"Ag 01","Rua n 01","83987654321");	
		Agencia salvo = _agenciaRepository.save(criado);
		
		assertEquals(esperado, salvo);
		
	}
	@Test
	void attEDeleteAgencia() {
		List<Agencia> list=null;
		Agencia agencia= new Agencia(1L,"Ag 01","Rua n 01","83987654321");
		agencia.setNomeAgencia("Ag 02");
		agencia.setEndereco("Rua n 02");
		Agencia esperado = new Agencia(1L,"Ag 02","Rua n 02","83987654321");	
		Agencia salvo = _agenciaRepository.save(agencia);
		assertEquals(esperado, salvo);
	
		Agencia agencia2= new Agencia(2L,"Agencia 5","Rua das Ladeiras","83987654321");
		agencia2.setTelefone("83741258963");
		Agencia esperado2 = new Agencia(2L,"Agencia 5","Rua das Ladeiras","83741258963");	
		Agencia salvo2 = _agenciaRepository.save(agencia2);
		assertEquals(esperado2, salvo2);
		
		//delete
		_agenciaRepository.delete(salvo2);
	
		
	}
	

}
