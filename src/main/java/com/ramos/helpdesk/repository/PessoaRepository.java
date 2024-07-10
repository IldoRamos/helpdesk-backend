package com.ramos.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramos.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
	
	

}
