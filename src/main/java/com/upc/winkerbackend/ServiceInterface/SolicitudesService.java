package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.SolicitudesDTO;
import com.upc.winkerbackend.Entities.Solicitud;

import java.util.List;

public interface SolicitudesService {
    public void insertarSolicitudes (Solicitud solicitudes);
    public void eliminarSolicitudes (Long id);
    public void actualizarSolicitudes (Solicitud solicitudes);
    public List<SolicitudesDTO> listarSolicitudesxPostulante(Long postulanteid);
    public List<SolicitudesDTO> listarSolicitudxId(Long id);
    long contarSolicitudesEnviadas();
    long contarPostulacionesPorPostulante(Long postulanteId);
    List<SolicitudesDTO> solicitudesRecibidasPorOferta(Long ofertaEmpleoId);
    List<SolicitudesDTO> solicitudesAceptadas();
}
