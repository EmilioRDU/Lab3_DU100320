package com.mitocode.controller;


import com.mitocode.dto.ExamDTO;
import com.mitocode.dto.JugadorDTO;
import com.mitocode.dto.PosicionDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Jugador;
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
@RequestMapping("/jugador")
public class JugadorController {

    @Autowired
    private IJugadorService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> findAll() {
        List<JugadorDTO> list = service.findAll().stream()
                .map(Jugador -> {
                    JugadorDTO JugadorDTO = mapper.map(Jugador, JugadorDTO.class);
                    // Mapea manualmente la relación Position
                    JugadorDTO.setPosicionDTO(mapper.map(Jugador.getPosicion(), PosicionDTO.class));
                    return JugadorDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> findById(@PathVariable("id") Integer id) {
        JugadorDTO dtoResponse;
        Jugador obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, JugadorDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody JugadorDTO dto) {
        Jugador p = service.save(mapper.map(dto, Jugador.class));
        //localhost:8080/medics/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdJugador()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Jugador> update(@Valid @RequestBody JugadorDTO dto) {
        Jugador obj = service.findById(dto.getIdJugador());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdJugador());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Jugador.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Jugador obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
