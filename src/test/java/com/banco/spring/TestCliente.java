package com.banco.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.spring.model.Cliente;
import com.banco.spring.repository.ClienteRepository;

@SpringBootTest
public class TestCliente {
	@Autowired
	private ClienteRepository _clienteRepository;
	@Test
	void criarCliente() {
		
		Long id = 1L;
		String nome = "Kamila";
		String cpf = "12345678985";
		String telefone = "83987654321";
		
		Cliente criado = new Cliente(id,nome,cpf,telefone);
		Cliente esperado = new Cliente(1L,"Kamila","12345678985","83987654321");
		Cliente salvo = _clienteRepository.save(criado);
		
		assertEquals(esperado, salvo);
		
	}

}
