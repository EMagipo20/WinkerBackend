package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.TipoTrabajoDTO;
import com.upc.winkerbackend.Entities.TipoTrabajo;
import com.upc.winkerbackend.ServiceImplements.TipoTrabajoServiceImplements;
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
@RequestMapping("/TipoTrabajo")
@PreAuthorize("hasAnyAuthority('EMPRESA')")
public class TipoTrabajoController {
    @Autowired
    private TipoTrabajoServiceImplements tS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody TipoTrabajoDTO tipoTrabajoDTO) {
        try {
            TipoTrabajo tipoTrabajo = modelMapper.map(tipoTrabajoDTO, TipoTrabajo.class);
            tS.insertarTipoTrabajo(tipoTrabajo);
            return ResponseEntity.ok("Tipo de trabajo agregado con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar el tipo de trabajo: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al agregar tipo de trabajo.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody TipoTrabajoDTO tipoTrabajoDTO) {
        try {
            TipoTrabajo tipoTrabajo = modelMapper.map(tipoTrabajoDTO, TipoTrabajo.class);
            tS.actualizarTipoTrabajo(tipoTrabajo);
            return ResponseEntity.ok("Tipo de trabajo actualizado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Tipo de trabajo no encontrado para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar el tipo de trabajo: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al actualizar tipo de trabajo.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            tS.eliminarTipoTrabajo(id);
            return ResponseEntity.ok("Tipo de trabajo eliminado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Tipo de trabajo no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al eliminar tipo de trabajo.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<TipoTrabajoDTO>> listarTodo() {
        try {
            List<TipoTrabajoDTO> tiposTrabajo = tS.listarTipoTrabajos();
            return ResponseEntity.ok(tiposTrabajo);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al listar tipos de trabajo.");
        }
    }
}