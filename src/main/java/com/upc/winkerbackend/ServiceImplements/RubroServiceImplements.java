package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.RubroDTO;
import com.upc.winkerbackend.Entities.Rubro;
import com.upc.winkerbackend.Repositories.RubroRepository;
import com.upc.winkerbackend.ServiceInterface.RubroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RubroServiceImplements implements RubroService {

    @Autowired
    private RubroRepository rR;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertarRubro(Rubro rubro) {
        rR.save(rubro);
    }

    @Override
    public void actualizarRubro(Rubro rubro) { rR.save(rubro); }

    @Override
    public void eliminarRubro(Long id) {
        rR.deleteById(id);
    }

    @Override
    public List<RubroDTO> listarRubros() {
        return rR.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countAllRubros() {
        return rR.countAllRubros();
    }

    @Override
    public Rubro findByNombreRubro(String nombre) {
        return rR.findByNombreRubro(nombre);
    }

    private RubroDTO convertToDTO(Rubro rubro) {
        return modelMapper.map(rubro, RubroDTO.class);
    }
}
