package com.banco.spring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.banco.spring.model.Agencia;
import com.banco.spring.repository.AgenciaRepository;
import com.banco.spring.repository.ClienteRepository;
import com.banco.spring.repository.ContaCorrenteRepository;
import com.banco.spring.repository.ExtratoRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private AgenciaRepository agenciaRep;

	@Autowired
	private ClienteRepository clienteRep;

	@Autowired
	private ContaCorrenteRepository ContaRep;

	@Autowired
	private ExtratoRepository ExtratoRep;

	@Override
	public void run(String... args) throws Exception {
		Agencia agencia = new Agencia(null, "Agencia 1", "Rua das Baratas", "83 987564123");
		agenciaRep.saveAll(Arrays.asList(agencia));
	}
}
