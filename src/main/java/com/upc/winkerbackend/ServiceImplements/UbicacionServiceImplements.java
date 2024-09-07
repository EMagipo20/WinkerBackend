package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.UbicacionOfertaDTO;
import com.upc.winkerbackend.Entities.UbicacionOferta;
import com.upc.winkerbackend.Repositories.UbicacionOfertaRepository;
import com.upc.winkerbackend.ServiceInterface.UbicacionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UbicacionServiceImplements implements UbicacionService {
    @Autowired
    private UbicacionOfertaRepository uR;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertarUbicacion(UbicacionOferta ubicacionOferta) {
        uR.save(ubicacionOferta);
    }

    @Override
    public void actualizarUbicacion(UbicacionOferta ubicacionOferta) {
        uR.save(ubicacionOferta);
    }

    @Override
    public void eliminarUbicacion(Long id) {
        uR.deleteById(id);
    }

    @Override
    public List<UbicacionOfertaDTO> listarUbicaciones() {
        return uR.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UbicacionOferta> findByDistrito(String distrito) {
        return uR.findByDistrito(distrito);
    }

    @Override
    public List<UbicacionOferta> findByDepartamento(String departamento) {
        return uR.findByDepartamento(departamento);
    }

    @Override
    public List<UbicacionOferta> findByDireccion(String direccion) {
        return uR.findByDireccion(direccion);
    }

    private UbicacionOfertaDTO convertToDTO(UbicacionOferta ubicacionOferta) {
        return modelMapper.map(ubicacionOferta, UbicacionOfertaDTO.class);
    }
}
