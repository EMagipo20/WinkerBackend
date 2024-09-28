package com.upc.winkerbackend.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDTO {
    private Long id;
    private String pregunta;
    private String respuesta;
    private LocalDate fechaPublicacion;
    //FK
    private Long postulanteId;
    private Long ofertaEmpleoId;
}
