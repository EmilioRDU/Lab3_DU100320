package com.mitocode.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JugadorDTO {

    private Integer idJugador;

    @NotNull
    @Size(min = 3, message = "{nombre.size}")
    private String nombre;
    private Integer numero;
    private PosicionDTO posicionDTO;


}
