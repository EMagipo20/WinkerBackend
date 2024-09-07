package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.SolicitudesDTO;
import com.upc.winkerbackend.Entities.Solicitud;
import com.upc.winkerbackend.ServiceImplements.SolicitudesServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Solicitudes")
@PreAuthorize("hasAnyAuthority('POSTULANTE', 'EMPRESA')")
public class SolicitudesController {
    @Autowired
    private SolicitudesServiceImplements solicitudesService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody SolicitudesDTO solicitudesDTO) {
        Solicitud solicitudes = modelMapper.map(solicitudesDTO, Solicitud.class);
        solicitudesService.insertarSolicitudes(solicitudes);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody SolicitudesDTO solicitudesDTO) {
        Solicitud solicitudes = modelMapper.map(solicitudesDTO, Solicitud.class);
        solicitudesService.actualizarSolicitudes(solicitudes);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        solicitudesService.eliminarSolicitudes(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarPorPostulante/{postulanteId}")
    public ResponseEntity<List<SolicitudesDTO>> listarPorPostulante(@PathVariable("postulanteId") Long postulanteId) {
        List<SolicitudesDTO> solicitudes = solicitudesService.listarSolicitudesxPostulante(postulanteId);
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/ListarPorId/{id}")
    public ResponseEntity<List<SolicitudesDTO>> listarPorId(@PathVariable("id") Long id) {
        List<SolicitudesDTO> solicitudes = solicitudesService.listarSolicitudxId(id);
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/ContarEnviadas")
    public ResponseEntity<Long> contarEnviadas() {
        long count = solicitudesService.contarSolicitudesEnviadas();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/ContarPorPostulante/{postulanteId}")
    public ResponseEntity<Long> contarPorPostulante(@PathVariable("postulanteId") Long postulanteId) {
        long count = solicitudesService.contarPostulacionesPorPostulante(postulanteId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/RecibidasPorOferta/{ofertaEmpleoId}")
    public ResponseEntity<List<SolicitudesDTO>> recibidasPorOferta(@PathVariable("ofertaEmpleoId") Long ofertaEmpleoId) {
        List<SolicitudesDTO> solicitudes = solicitudesService.solicitudesRecibidasPorOferta(ofertaEmpleoId);
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/Aceptadas")
    public ResponseEntity<List<SolicitudesDTO>> solicitudesAceptadas() {
        List<SolicitudesDTO> solicitudes = solicitudesService.solicitudesAceptadas();
        return ResponseEntity.ok(solicitudes);
    }
}
