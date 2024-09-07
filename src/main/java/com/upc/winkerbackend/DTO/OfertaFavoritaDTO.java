package com.upc.winkerbackend.DTO;

import lombok.Data;

@Data
public class OfertaFavoritaDTO {
    private Long id;
    //FK
    private Long ofertaEmpleoId;
    private Long postulanteId;
}
