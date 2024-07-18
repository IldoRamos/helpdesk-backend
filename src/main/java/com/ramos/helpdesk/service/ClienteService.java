package com.ramos.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramos.helpdesk.domain.Cliente;
import com.ramos.helpdesk.domain.Pessoa;
import com.ramos.helpdesk.domain.DTO.ClienteDTO;
import com.ramos.helpdesk.repository.ClienteRepository;
import com.ramos.helpdesk.repository.PessoaRepository;
import com.ramos.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.ramos.helpdesk.service.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository tecnicoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado id: "+id));
	}
	public List<Cliente> findAll() {
		return tecnicoRepository.findAll();
	}
	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return tecnicoRepository.save(newObj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		validaPorCpfEEmail(objDTO);
		oldObj = new Cliente(objDTO);
		
		return tecnicoRepository.save(oldObj);
	}
	
	public void delite(Integer id) {
		Cliente obj = findById(id);
		if(obj.getChamado().size()>0) {
			throw new DataIntegrityViolationException("Cliente possui ordem de serviço e não pode ser deletado!");
		}
		
		tecnicoRepository.deleteById(id);
	}
	
	private void validaPorCpfEEmail(ClienteDTO objDTO) {
		
		Optional<Pessoa> obj =  pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId()!=objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF ja cadastrado no sistemas");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId()!=objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail ja cadastrado no sistemas");
		}
	}
	
	

	
}
