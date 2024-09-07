package com.upc.winkerbackend.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SolicitudesDTO {
    private Long id;
    private LocalDate fechaSolicitud;
    private String estadoSolicitud;
    private String tituloOferta;
    //
    private Long ofertaEmpleoId;
    private Long postulanteId;
}
