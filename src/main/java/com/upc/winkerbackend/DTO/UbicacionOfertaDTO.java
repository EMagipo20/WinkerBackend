package com.upc.winkerbackend.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UbicacionOfertaDTO {
    private Long id;
    private String departamento;
    private String distrito;
    private String direccion;
}
