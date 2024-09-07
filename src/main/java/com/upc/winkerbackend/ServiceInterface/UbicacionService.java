package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.UbicacionOfertaDTO;
import com.upc.winkerbackend.Entities.UbicacionOferta;

import java.util.List;

public interface UbicacionService {
    public void insertarUbicacion(UbicacionOferta ubicacionOferta);
    public void actualizarUbicacion(UbicacionOferta ubicacionOferta);
    public void eliminarUbicacion(Long id);
    List<UbicacionOfertaDTO> listarUbicaciones();
    List<UbicacionOferta> findByDistrito(String distrito);
    List<UbicacionOferta> findByDepartamento(String departamento);
    List<UbicacionOferta> findByDireccion(String direccion);
}
