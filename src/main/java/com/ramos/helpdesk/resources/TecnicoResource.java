package com.ramos.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ramos.helpdesk.domain.Tecnico;
import com.ramos.helpdesk.domain.DTO.TecnicoDTO;
import com.ramos.helpdesk.service.TecnicoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/tecnicos")
@CrossOrigin(origins = "http://localhost:4200")
public class TecnicoResource {

	@Autowired
	private TecnicoService service;
	@GetMapping(value = "/{id}") //variavel de path
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
		Tecnico obj = service.findById(id);
		
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	} 
	
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){
		List<Tecnico> lista = service.findAll();
		
		List<TecnicoDTO> listaDTO = lista.stream().map(obj->new TecnicoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){
		
		Tecnico newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id")
				.buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){
		Tecnico Obj = service.update(id,objDTO);
		
		return ResponseEntity.ok().body(new TecnicoDTO(Obj));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> detete(@PathVariable Integer id){
		service.delite(id);
		
		return ResponseEntity.noContent().build();
	}
}
