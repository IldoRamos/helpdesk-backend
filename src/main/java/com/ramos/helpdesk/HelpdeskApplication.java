package com.ramos.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ramos.helpdesk.domain.Chamado;
import com.ramos.helpdesk.domain.Cliente;
import com.ramos.helpdesk.domain.Tecnico;
import com.ramos.helpdesk.domain.enums.Perfil;
import com.ramos.helpdesk.domain.enums.Prioridade;
import com.ramos.helpdesk.domain.enums.Status;
import com.ramos.helpdesk.repository.ChamadoRepository;
import com.ramos.helpdesk.repository.ClienteRepository;
import com.ramos.helpdesk.repository.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null,"Ramos Vieira", "511.815.050-70","ramos@gmail.com","123" );
		tec1.addPerfis(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null,"Joao Fernandes", "159.797.560-51","jp@gmail.com","123" );
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
		
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
		
		
		
	}

}
