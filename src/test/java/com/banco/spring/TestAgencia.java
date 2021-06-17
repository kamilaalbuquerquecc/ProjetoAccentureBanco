package com.banco.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.spring.model.Agencia;
import com.banco.spring.repository.AgenciaRepository;

@SpringBootTest
public class TestAgencia {
	@Autowired
	private AgenciaRepository _agenciaRepository=null;
	
	@Test
	void criarAgencia() {
		
		Long idAgencia = 8L;
		String nomeAgencia = "Ag 01";
		String endereco = "Rua n 01";
		String telefone = "83987654321";
		
		Agencia criado = new Agencia(idAgencia,nomeAgencia,endereco,telefone);
		Agencia esperado = new Agencia(8L,"Ag 01","Rua n 01","83987654321");	
		Agencia salvo = _agenciaRepository.save(criado);
		assertEquals(esperado, salvo);
		
	}
	@Test
	void atualizarAgencia() {
	
		Agencia agencia= new Agencia(9L,"Ag 01","Rua n 01","83987654321");
		agencia.setNomeAgencia("Ag 02");
		agencia.setEndereco("Rua n 02");
		Agencia esperado= new Agencia(9L,"Ag 02","Rua n 02","83987654321");	
		Agencia salvo = _agenciaRepository.save(agencia);
		assertEquals(esperado, salvo);
		
		
	}
	
	@Test
	void deleteAgencia() {
		Agencia agencia= new Agencia(10L,"Ag 01","Rua n 01","83987654321");
		_agenciaRepository.save(agencia);
		String teste = "testando";

		Optional<Agencia> achando = _agenciaRepository.findById(agencia.getIdAgencia());

		//DELETE PELO ID
		if (achando.isPresent()) {
			_agenciaRepository.delete(achando.get());
		}

		//VERIFICANDO SE ID NÃO EXISTE MAIS
		if (!achando.isPresent()) {
			teste = null;
		}

		assertNull(teste);

	

	}
	

}