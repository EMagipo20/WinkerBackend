package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.RubroDTO;
import com.upc.winkerbackend.Entities.Rubro;
import com.upc.winkerbackend.ServiceImplements.RubroServiceImplements;
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
@RequestMapping("/Rubros")
@PreAuthorize("hasAnyAuthority('EMPRESA')")
public class RubroController {
    @Autowired
    private RubroServiceImplements rS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody RubroDTO rubroDTO) {
        try {
            Rubro rubro = modelMapper.map(rubroDTO, Rubro.class);
            rS.insertarRubro(rubro);
            return ResponseEntity.ok("Rubro agregado con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar el rubro: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al agregar rubro.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody RubroDTO rubroDTO) {
        try {
            Rubro rubro = modelMapper.map(rubroDTO, Rubro.class);
            rS.actualizarRubro(rubro);
            return ResponseEntity.ok("Rubro actualizado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Rubro no encontrado para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar el rubro: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al actualizar rubro.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            rS.eliminarRubro(id);
            return ResponseEntity.ok("Rubro eliminado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Rubro no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al eliminar rubro.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<RubroDTO>> listarTodo() {
        try {
            List<RubroDTO> rubros = rS.listarRubros();
            return ResponseEntity.ok(rubros);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al listar rubros.");
        }
    }

    @GetMapping("/ContarRubros")
    public ResponseEntity<Long> getCountAllRubros() {
        try {
            Long count = rS.countAllRubros();
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al contar rubros.");
        }
    }

    @GetMapping("/nombre")
    public ResponseEntity<Rubro> getByNombre(@RequestParam String nombre) {
        try {
            Rubro rubro = rS.findByNombreRubro(nombre);
            if (rubro == null) {
                throw new NotFoundException("Rubro con el nombre especificado no encontrado.");
            }
            return ResponseEntity.ok(rubro);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al obtener rubro por nombre.");
        }
    }
}
