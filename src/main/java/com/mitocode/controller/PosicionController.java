package com.mitocode.controller;


import com.mitocode.dto.ExamDTO;
import com.mitocode.dto.MedicDTO;
import com.mitocode.dto.PosicionDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Medic;
import com.mitocode.model.Posicion;
import com.mitocode.service.IJugadorService;
import com.mitocode.service.IPosicionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posicion")
public class PosicionController {

    @Autowired
    private IPosicionService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PosicionDTO>> findAll() {
        List<PosicionDTO> list = service.findAll().stream().map(p -> mapper.map(p, PosicionDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosicionDTO> findById(@PathVariable("id") Integer id) {
        PosicionDTO dtoResponse;
        Posicion obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, PosicionDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PosicionDTO dto) {
        Posicion p = service.save(mapper.map(dto, Posicion.class));
        //localhost:8080/medics/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdPosicion()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Posicion> update(@Valid @RequestBody PosicionDTO dto) {
        Posicion obj = service.findById(dto.getIdPosicion());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdPosicion());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Posicion.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Posicion obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
