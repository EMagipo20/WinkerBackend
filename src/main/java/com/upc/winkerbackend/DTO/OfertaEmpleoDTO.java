package com.upc.winkerbackend.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OfertaEmpleoDTO {
    private Long id;
    private String tituloTrabajo;
    private String descripcion;
    private Double salario;
    private LocalDate fechaPublicacion;
    private LocalDate fechaVencimiento;
    private boolean indActivo;
    //FK
    private Long empresaId;
    private Long tipoTrabajoId;
    private Long ubicacionOfertaId;
}
