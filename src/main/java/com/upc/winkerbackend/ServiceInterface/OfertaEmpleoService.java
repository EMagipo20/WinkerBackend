package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.OfertaEmpleoDTO;
import com.upc.winkerbackend.Entities.OfertaEmpleo;

import java.util.List;

public interface OfertaEmpleoService {
    public void insertarOferta (OfertaEmpleo ofertaEmpleo);
    public void actualizarOferta (OfertaEmpleo ofertaEmpleo);
    public List<OfertaEmpleoDTO> listarOfertasEmpleo();
    public void eliminarOferta (Long id);
    List<OfertaEmpleoDTO> buscarOfertasPorTitulo(String tituloTrabajo);
    List<OfertaEmpleoDTO> ofertasConMejoresSalarios();
    long contarOfertasPorMesYAno(int mes, int ano);
}
