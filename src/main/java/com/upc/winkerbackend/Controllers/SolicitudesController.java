package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.SolicitudesDTO;
import com.upc.winkerbackend.Entities.Solicitud;
import com.upc.winkerbackend.ServiceImplements.SolicitudesServiceImplements;
import com.upc.winkerbackend.exceptions.DataException;
import com.upc.winkerbackend.exceptions.ErrorException;
import com.upc.winkerbackend.exceptions.NotFoundException;
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
        try {
            Solicitud solicitudes = modelMapper.map(solicitudesDTO, Solicitud.class);
            solicitudesService.insertarSolicitudes(solicitudes);
            return ResponseEntity.ok("Solicitud agregada con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar solicitud: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al agregar solicitud.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody SolicitudesDTO solicitudesDTO) {
        try {
            Solicitud solicitudes = modelMapper.map(solicitudesDTO, Solicitud.class);
            solicitudesService.actualizarSolicitudes(solicitudes);
            return ResponseEntity.ok("Solicitud actualizada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Solicitud no encontrada para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar solicitud: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al actualizar solicitud.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            solicitudesService.eliminarSolicitudes(id);
            return ResponseEntity.ok("Solicitud eliminada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Solicitud no encontrada para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al eliminar solicitud.");
        }
    }

    @GetMapping("/ListarPorPostulante/{postulanteId}")
    public ResponseEntity<List<SolicitudesDTO>> listarPorPostulante(@PathVariable("postulanteId") Long postulanteId) {
        try {
            List<SolicitudesDTO> solicitudes = solicitudesService.listarSolicitudesxPostulante(postulanteId);
            return ResponseEntity.ok(solicitudes);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al listar solicitudes por postulante.");
        }
    }

    @GetMapping("/ListarPorId/{id}")
    public ResponseEntity<List<SolicitudesDTO>> listarPorId(@PathVariable("id") Long id) {
        try {
            List<SolicitudesDTO> solicitudes = solicitudesService.listarSolicitudxId(id);
            return ResponseEntity.ok(solicitudes);
        } catch (NotFoundException ex) {
            throw new NotFoundException("Solicitud no encontrada por ID: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al listar solicitudes por ID.");
        }
    }

    @GetMapping("/ContarEnviadas")
    public ResponseEntity<Long> contarEnviadas() {
        try {
            long count = solicitudesService.contarSolicitudesEnviadas();
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al contar solicitudes enviadas.");
        }
    }

    @GetMapping("/ContarPorPostulante/{postulanteId}")
    public ResponseEntity<Long> contarPorPostulante(@PathVariable("postulanteId") Long postulanteId) {
        try {
            long count = solicitudesService.contarPostulacionesPorPostulante(postulanteId);
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al contar postulaciones por postulante.");
        }
    }

    @GetMapping("/RecibidasPorOferta/{ofertaEmpleoId}")
    public ResponseEntity<List<SolicitudesDTO>> recibidasPorOferta(@PathVariable("ofertaEmpleoId") Long ofertaEmpleoId) {
        try {
            List<SolicitudesDTO> solicitudes = solicitudesService.solicitudesRecibidasPorOferta(ofertaEmpleoId);
            return ResponseEntity.ok(solicitudes);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al obtener solicitudes recibidas por oferta.");
        }
    }

    @GetMapping("/Aceptadas")
    public ResponseEntity<List<SolicitudesDTO>> solicitudesAceptadas() {
        try {
            List<SolicitudesDTO> solicitudes = solicitudesService.solicitudesAceptadas();
            return ResponseEntity.ok(solicitudes);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al obtener solicitudes aceptadas.");
        }
    }
}
