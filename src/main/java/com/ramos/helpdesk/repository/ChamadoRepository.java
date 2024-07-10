package com.ramos.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramos.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{
	
	

}
