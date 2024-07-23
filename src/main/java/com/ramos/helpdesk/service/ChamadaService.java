package com.ramos.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramos.helpdesk.domain.Chamado;
import com.ramos.helpdesk.repository.ChamadoRepository;
import com.ramos.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class ChamadaService {

	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado ID: "+id));
	}
	

	public List<Chamado> findAll(){
		return repository.findAll();
	
	}
}
