package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.RubroDTO;
import com.upc.winkerbackend.Entities.Rubro;

import java.util.List;


public interface RubroService {
    public void insertarRubro (Rubro rubro);
    public void actualizarRubro (Rubro rubro);
    public void eliminarRubro (Long id);
    List<RubroDTO> listarRubros();
    Long countAllRubros();
    Rubro findByNombreRubro(String nombreRubro);
}
