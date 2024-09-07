package com.upc.winkerbackend.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DocumentoDTO {
    private Long id;
    private String tipo;
    private LocalDate fechaCarga;
    private String archivoBase64;
    //FK
    private Long postulanteId;
}
