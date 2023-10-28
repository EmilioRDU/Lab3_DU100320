package com.mitocode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJugador;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private Integer numero;
    @ManyToOne
    @JoinColumn(name = "idPosicion", nullable = false)
    private Posicion posicion;
}
