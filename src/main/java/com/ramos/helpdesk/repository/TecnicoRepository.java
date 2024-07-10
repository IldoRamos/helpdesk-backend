package com.ramos.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramos.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{
	
	

}
