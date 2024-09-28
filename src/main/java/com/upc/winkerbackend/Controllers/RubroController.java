package com.upc.winkerbackend.Controllers;

// Importaciones necesarias para manejar DTOs, entidades, servicios, excepciones y utilidades de Spring y ModelMapper
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

@RestController // Anotación que marca la clase como un controlador REST
@RequestMapping("/Rubros") // Define el endpoint base para los servicios relacionados a Rubros
@PreAuthorize("hasAnyAuthority('EMPRESA')") // Restringe el acceso a usuarios con la autoridad 'EMPRESA'
public class RubroController {

    @Autowired // Inyección automática del servicio de Rubro
    private RubroServiceImplements rS;

    @Autowired // Inyección de ModelMapper para convertir entre DTOs y entidades
    private ModelMapper modelMapper;

    // Endpoint para agregar un nuevo rubro
    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody RubroDTO rubroDTO) {
        try {
            // Convierte el DTO en una entidad de tipo Rubro
            Rubro rubro = modelMapper.map(rubroDTO, Rubro.class);
            // Llama al servicio para insertar el nuevo rubro
            rS.insertarRubro(rubro);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Rubro agregado con éxito.");
        } catch (DataException ex) {
            // Manejo de excepción específica de datos
            throw new DataException("Error al agregar el rubro: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al agregar rubro.");
        }
    }

    // Endpoint para actualizar un rubro existente
    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody RubroDTO rubroDTO) {
        try {
            // Convierte el DTO en una entidad de tipo Rubro
            Rubro rubro = modelMapper.map(rubroDTO, Rubro.class);
            // Llama al servicio para actualizar el rubro
            rS.actualizarRubro(rubro);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Rubro actualizado con éxito.");
        } catch (NotFoundException ex) {
            // Manejo de excepción si el rubro no es encontrado
            throw new NotFoundException("Rubro no encontrado para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            // Manejo de excepción para errores de datos
            throw new DataException("Error al actualizar el rubro: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al actualizar rubro.");
        }
    }

    // Endpoint para eliminar un rubro por su ID
    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            // Llama al servicio para eliminar el rubro
            rS.eliminarRubro(id);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Rubro eliminado con éxito.");
        } catch (NotFoundException ex) {
            // Manejo de excepción si el rubro no es encontrado
            throw new NotFoundException("Rubro no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al eliminar rubro.");
        }
    }

    // Endpoint para listar todos los rubros
    @GetMapping("/ListarTodo")
    public ResponseEntity<List<RubroDTO>> listarTodo() {
        try {
            // Llama al servicio para listar todos los rubros
            List<RubroDTO> rubros = rS.listarRubros();
            // Retorna la lista de rubros con una respuesta HTTP 200
            return ResponseEntity.ok(rubros);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Error interno al listar rubros.");
        }
    }

    // Endpoint para contar el número total de rubros
    @GetMapping("/ContarRubros")
    public ResponseEntity<Long> getCountAllRubros() {
        try {
            // Llama al servicio para contar todos los rubros
            Long count = rS.countAllRubros();
            // Retorna el conteo con una respuesta HTTP 200
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Error interno al contar rubros.");
        }
    }

    // Endpoint para obtener un rubro por su nombre
    @GetMapping("/nombre")
    public ResponseEntity<Rubro> getByNombre(@RequestParam String nombre) {
        try {
            // Llama al servicio para obtener un rubro por su nombre
            Rubro rubro = rS.findByNombreRubro(nombre);
            if (rubro == null) {
                throw new NotFoundException("Rubro con el nombre especificado no encontrado.");
            }
            // Retorna el rubro si es encontrado
            return ResponseEntity.ok(rubro);
        } catch (NotFoundException ex) {
            // Retorna una respuesta HTTP 404 si no se encuentra el rubro
            return ResponseEntity.status(404).body(null);
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al obtener rubro por nombre.");
        }
    }
}
