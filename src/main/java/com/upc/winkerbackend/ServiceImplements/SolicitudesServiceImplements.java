package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.SolicitudesDTO;
import com.upc.winkerbackend.Entities.Solicitud;
import com.upc.winkerbackend.Repositories.SolicitudesRepository;
import com.upc.winkerbackend.ServiceInterface.SolicitudesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudesServiceImplements implements SolicitudesService {
    @Autowired
    private SolicitudesRepository solicitudesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertarSolicitudes(Solicitud solicitudes) {
        solicitudesRepository.save(solicitudes);
    }

    @Override
    public void eliminarSolicitudes(Long id) {
        solicitudesRepository.deleteById(id);
    }

    @Override
    public void actualizarSolicitudes( Solicitud solicitud) {
        solicitudesRepository.save(solicitud);
    }

    @Override
    public List<SolicitudesDTO> listarSolicitudesxPostulante(Long id) {
        return solicitudesRepository.findByPostulanteId(id)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SolicitudesDTO> listarSolicitudxId(Long id) {
        return solicitudesRepository.findById(id)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long contarSolicitudesEnviadas() {
        return solicitudesRepository.countTotalSolicitudes();
    }

    @Override
    public long contarPostulacionesPorPostulante(Long postulanteId) {
        return solicitudesRepository.countSolicitudesByPostulanteId(postulanteId);
    }

    @Override
    public List<SolicitudesDTO> solicitudesRecibidasPorOferta(Long ofertaEmpleoId) {
        return solicitudesRepository.findByOfertaEmpleoId(ofertaEmpleoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SolicitudesDTO> solicitudesAceptadas() {
        return solicitudesRepository.findSolicitudesAceptadas()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SolicitudesDTO convertToDTO(Solicitud solicitud) {
        return modelMapper.map(solicitud, SolicitudesDTO.class);
    }


    @Override
    public List<SolicitudesDTO> listarSolicitudesPorEstado(String estado) {
        return solicitudesRepository.findSolicitudesPorEstado(estado)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}