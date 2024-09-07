package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.OfertaFavoritaDTO;
import com.upc.winkerbackend.Entities.OfertaFavorita;

import java.util.List;

public interface OfertaFavoritaService {
    public void insertarFavorita (OfertaFavorita ofertaFavorita);
    public void eliminarFavorita (Long id);
    List<OfertaFavoritaDTO> listarFavoritas();
    long contarOfertasFavoritas();
}
