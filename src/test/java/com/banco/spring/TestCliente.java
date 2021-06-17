package com.banco.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.spring.model.Cliente;
import com.banco.spring.repository.ClienteRepository;

@SpringBootTest
public class TestCliente {
	@Autowired
	private ClienteRepository _clienteRepository = null;

	@Test
	void criarCliente() {

		Long id = 1L;
		String nome = "Kamila";
		String cpf = "12345678985";
		String telefone = "83987654321";

		Cliente criado = new Cliente(id, nome, cpf, telefone);
		Cliente esperado = new Cliente(1L, "Kamila", "12345678985", "83987654321");
		Cliente salvo = _clienteRepository.save(criado);

		assertEquals(esperado, salvo);

	}

	@Test
	void atualizarCliente() {

		Cliente cliente = new Cliente(2L, "Kamila", "12345678985", "83987654321");
		cliente.setNome("Sabrina");

		Cliente esperado = new Cliente(2L, "Sabrina", "12345678985", "83987654321");
		Cliente salvo = _clienteRepository.save(cliente);

		assertEquals(esperado, salvo);

	}

	@Test
	void deleteCliente() {
		Cliente cliente = new Cliente(3L, "Kamila", "12345678985", "83987654321");
		_clienteRepository.save(cliente);
		String teste = "testando";

		Optional<Cliente> achando = _clienteRepository.findById(cliente.getId());

		//DELETE PELO ID
		if (achando.isPresent()) {
			_clienteRepository.delete(achando.get());
		}

		//VERIFICANDO SE ID NÃO EXISTE MAIS
		if (!achando.isPresent()) {
			teste = null;
		}

		assertNull(teste);

	}

}
