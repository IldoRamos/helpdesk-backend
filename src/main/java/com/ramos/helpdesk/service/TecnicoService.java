package com.ramos.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ramos.helpdesk.domain.Pessoa;
import com.ramos.helpdesk.domain.Tecnico;
import com.ramos.helpdesk.domain.DTO.TecnicoDTO;
import com.ramos.helpdesk.repository.PessoaRepository;
import com.ramos.helpdesk.repository.TecnicoRepository;
import com.ramos.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.ramos.helpdesk.service.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado id: "+id));
	}
	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}
	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return tecnicoRepository.save(newObj);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		
		return tecnicoRepository.save(oldObj);
	}
	
	public void delite(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getChamado().size()>0) {
			throw new DataIntegrityViolationException("Tecnico possui ordem de serviço e não pode ser deletado!");
		}
		
		tecnicoRepository.deleteById(id);
	}
	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		
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
