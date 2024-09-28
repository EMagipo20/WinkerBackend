package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.UbicacionOfertaDTO;
import com.upc.winkerbackend.Entities.UbicacionOferta;
import com.upc.winkerbackend.ServiceImplements.UbicacionServiceImplements;
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
@RequestMapping("/UbicacionOferta")
@PreAuthorize("hasAnyAuthority('EMPRESA')")
public class UbicacionOfertaController {
    @Autowired
    private UbicacionServiceImplements uOS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody UbicacionOfertaDTO ubicacionOfertaDTO) {
        try {
            UbicacionOferta ubicacionOferta = modelMapper.map(ubicacionOfertaDTO, UbicacionOferta.class);
            uOS.insertarUbicacion(ubicacionOferta);
            return ResponseEntity.ok("Ubicación agregada con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar ubicación: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al agregar ubicación.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody UbicacionOfertaDTO ubicacionOfertaDTO) {
        try {
            UbicacionOferta ubicacionOferta = modelMapper.map(ubicacionOfertaDTO, UbicacionOferta.class);
            uOS.actualizarUbicacion(ubicacionOferta);
            return ResponseEntity.ok("Ubicación actualizada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Ubicación no encontrada para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar ubicación: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al actualizar ubicación.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            uOS.eliminarUbicacion(id);
            return ResponseEntity.ok("Ubicación eliminada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Ubicación no encontrada para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al eliminar ubicación.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<UbicacionOfertaDTO>> listarTodo() {
        try {
            List<UbicacionOfertaDTO> ubicaciones = uOS.listarUbicaciones();
            return ResponseEntity.ok(ubicaciones);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al listar ubicaciones.");
        }
    }

    @GetMapping("/distrito")
    public ResponseEntity<List<UbicacionOferta>> getByDistrito(@RequestParam String distrito) {
        try {
            List<UbicacionOferta> ubicaciones = uOS.findByDistrito(distrito);
            if (ubicaciones.isEmpty()) {
                throw new NotFoundException("No se encontraron ubicaciones para el distrito especificado.");
            }
            return ResponseEntity.ok(ubicaciones);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al buscar ubicaciones por distrito.");
        }
    }

    @GetMapping("/departamento")
    public ResponseEntity<List<UbicacionOferta>> getByDepartamento(@RequestParam String departamento) {
        try {
            List<UbicacionOferta> ubicaciones = uOS.findByDepartamento(departamento);
            if (ubicaciones.isEmpty()) {
                throw new NotFoundException("No se encontraron ubicaciones para el departamento especificado.");
            }
            return ResponseEntity.ok(ubicaciones);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al buscar ubicaciones por departamento.");
        }
    }

    @GetMapping("/direccion")
    public ResponseEntity<List<UbicacionOferta>> getByDireccion(@RequestParam String direccion) {
        try {
            List<UbicacionOferta> ubicaciones = uOS.findByDireccion(direccion);
            if (ubicaciones.isEmpty()) {
                throw new NotFoundException("No se encontraron ubicaciones para la dirección especificada.");
            }
            return ResponseEntity.ok(ubicaciones);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al buscar ubicaciones por dirección.");
        }
    }
}