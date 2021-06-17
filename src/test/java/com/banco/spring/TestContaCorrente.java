package com.banco.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.spring.model.ContaCorrente;
import com.banco.spring.repository.ContaCorrenteRepository;

@SpringBootTest
public class TestContaCorrente {
	@Autowired
	private ContaCorrenteRepository _contaCorrenteRepository=null;
	
	@Test
	void criarContaCorrente() {
		
		Long id = 20L;
		String numeroContaCorrente = "1258";
		double saldoContaCorrente = 10000.00;
		
		ContaCorrente criado = new ContaCorrente(id, numeroContaCorrente,saldoContaCorrente);
		
		ContaCorrente esperado = new ContaCorrente(20L,"1258",10000.00);
		ContaCorrente salvo = _contaCorrenteRepository.save(criado);
		
		assertEquals(criado, esperado);
		
	}
	
	@Test
	void atualizarContaCorrente() {
		
		ContaCorrente conta = new ContaCorrente(2L,"1258",10000.00);
		conta.setSaldoContaCorrente(15000.00);
		
		ContaCorrente esperado = new ContaCorrente(2L,"1258",15000.00);
		ContaCorrente salvo = _contaCorrenteRepository.save(conta);
		
		assertEquals(esperado, salvo);
		
	}
	@Test
	void deleteContaCorrente() {
		ContaCorrente conta = new ContaCorrente(3L,"1258",10000.00);
		_contaCorrenteRepository.save(conta);
		String teste = "testando";

		Optional<ContaCorrente> achando = _contaCorrenteRepository.findById(conta.getId());

		//DELETE PELO ID
		if (achando.isPresent()) {
			_contaCorrenteRepository.delete(achando.get());
		}

		//VERIFICANDO SE ID NÃO EXISTE MAIS
		if (!achando.isPresent()) {
			teste = null;
		}

		assertNull(teste);
	}

}
