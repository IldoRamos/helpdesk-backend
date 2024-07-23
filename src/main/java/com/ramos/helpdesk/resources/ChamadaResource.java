package com.ramos.helpdesk.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramos.helpdesk.domain.Chamado;
import com.ramos.helpdesk.domain.DTO.ChamadoDTO;
import com.ramos.helpdesk.service.ChamadaService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadaResource {

	@Autowired
	private ChamadaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
		Chamado obj = service.findById(id);
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}
}
