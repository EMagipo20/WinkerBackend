package com.upc.winkerbackend.DTO;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TipoTrabajoDTO {
    private Long id;
    private String tipo;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
