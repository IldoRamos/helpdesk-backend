package com.ramos.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramos.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	

}
