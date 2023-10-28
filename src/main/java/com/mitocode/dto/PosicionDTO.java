package com.mitocode.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PosicionDTO {

    private Integer idPosicion;

    @NotNull
    @Size(min = 3, message = "{nombre.size}")
    private String nombre;


}
