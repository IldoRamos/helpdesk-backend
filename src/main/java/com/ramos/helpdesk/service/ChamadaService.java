package com.ramos.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramos.helpdesk.domain.Chamado;
import com.ramos.helpdesk.domain.Cliente;
import com.ramos.helpdesk.domain.Tecnico;
import com.ramos.helpdesk.domain.DTO.ChamadoDTO;
import com.ramos.helpdesk.domain.enums.Prioridade;
import com.ramos.helpdesk.domain.enums.Status;
import com.ramos.helpdesk.repository.ChamadoRepository;
import com.ramos.helpdesk.service.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ChamadaService {

	@Autowired
	private ChamadoRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado ID: "+id));
	}
	

	public List<Chamado> findAll(){
		return repository.findAll();
	
	}


	public Chamado create(@Valid ChamadoDTO objDTO) {
		return repository.save(newchamado(objDTO));
	}
	
	private Chamado newchamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		Chamado chamado = new Chamado();
		
		if(obj.getId()!=null) {
			chamado.setId(obj.getId());
		}
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		return chamado;
	}
	 
}
