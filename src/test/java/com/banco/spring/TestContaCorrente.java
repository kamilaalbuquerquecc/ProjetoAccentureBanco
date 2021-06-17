package com.banco.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.spring.model.ContaCorrente;
import com.banco.spring.repository.ContaCorrenteRepository;

@SpringBootTest
public class TestContaCorrente {
	@Autowired
	private ContaCorrenteRepository _contaCorrenteRepository;
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
