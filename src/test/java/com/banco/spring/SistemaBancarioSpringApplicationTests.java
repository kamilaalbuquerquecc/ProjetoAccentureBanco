package com.banco.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.banco.spring.model.Agencia;
import com.banco.spring.model.Cliente;
import com.banco.spring.model.ContaCorrente;
import com.banco.spring.repository.AgenciaRepository;
import com.banco.spring.repository.ClienteRepository;
import com.banco.spring.repository.ContaCorrenteRepository;

@SpringBootTest
class SistemaBancarioSpringApplicationTests {
	
	@Autowired
	private ClienteRepository _clienteRepository;
	
	@Autowired
	private AgenciaRepository _agenciaRepository;
	
	@Autowired
	private ContaCorrenteRepository _contaCorrenteRepository;
	
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
	
	
	@Test
	void criarContaCorrente() {
		
		Long id = 1L;
		String numeroContaCorrente = "1258";
		double saldoContaCorrente = 10000.00;
		
		ContaCorrente criado = new ContaCorrente(id, numeroContaCorrente,saldoContaCorrente);
		ContaCorrente esperado = new ContaCorrente(1L,"1258",10000.00);
		ContaCorrente salvo = _contaCorrenteRepository.save(criado);
		
		assertEquals(esperado, salvo);
		
	}
}
