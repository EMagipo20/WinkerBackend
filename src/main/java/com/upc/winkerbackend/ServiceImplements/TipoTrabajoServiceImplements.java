package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.TipoTrabajoDTO;
import com.upc.winkerbackend.Entities.TipoTrabajo;
import com.upc.winkerbackend.Repositories.TipoTrabajoRepository;
import com.upc.winkerbackend.ServiceInterface.TipoTrabajoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoTrabajoServiceImplements implements TipoTrabajoService {

    @Autowired
    private TipoTrabajoRepository tR;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertarTipoTrabajo(TipoTrabajo tipoTrabajo) {
        tR.save(tipoTrabajo);
    }

    @Override
    public void actualizarTipoTrabajo(TipoTrabajo tipoTrabajo) {
        tR.save(tipoTrabajo);
    }

    @Override
    public void eliminarTipoTrabajo(Long id) {
        tR.deleteById(id);
    }

    @Override
    public List<TipoTrabajoDTO> listarTipoTrabajos() {
        return tR.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TipoTrabajoDTO convertToDTO(TipoTrabajo tipoTrabajo) {
        return modelMapper.map(tipoTrabajo, TipoTrabajoDTO.class);
    }
}
