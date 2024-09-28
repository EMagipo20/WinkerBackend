package com.upc.winkerbackend.Controllers;

// Importaciones necesarias para manejar DTOs, entidades, servicios e implementaciones, excepciones y otros componentes
import com.upc.winkerbackend.DTO.DocumentoDTO;
import com.upc.winkerbackend.Entities.DocumentoPostulante;
import com.upc.winkerbackend.ServiceImplements.DocumentoImplements;
import com.upc.winkerbackend.exceptions.DataException;
import com.upc.winkerbackend.exceptions.ErrorException;
import com.upc.winkerbackend.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Anotación para indicar que esta clase será un controlador REST
@RequestMapping("/Documentos") // Define el endpoint base para acceder a las rutas de esta clase
@PreAuthorize("hasAnyAuthority('POSTULANTE')") // Define que solo los usuarios con el rol 'POSTULANTE' pueden acceder a estos endpoints
public class DocumentoController {

    @Autowired // Inyección automática del servicio que implementa la lógica de negocio
    private DocumentoImplements dS;

    @Autowired // Inyección automática de ModelMapper para la conversión entre DTOs y entidades
    private ModelMapper modelMapper;

    // Endpoint para agregar un nuevo documento
    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody DocumentoDTO documentoDTO) {
        try {
            // Inserta el documento en la base de datos a través del servicio
            dS.insertarDocumento(documentoDTO);
            // Retorna una respuesta HTTP 200 (OK) con un mensaje de éxito
            return ResponseEntity.ok("Documento agregado con éxito.");
        } catch (DataException ex) {
            // Manejo de excepción personalizada para errores de datos
            throw new DataException("Error al agregar el documento: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Ocurrió un error interno al agregar el documento.");
        }
    }

    // Endpoint para actualizar un documento existente
    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody DocumentoDTO documentoDTO) {
        try {
            // Convierte el DTO en una entidad usando ModelMapper
            DocumentoPostulante documentoPostulante = modelMapper.map(documentoDTO, DocumentoPostulante.class);
            // Llama al servicio para actualizar el documento
            dS.actualizarDocumento(documentoPostulante);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Documento actualizado con éxito.");
        } catch (NotFoundException ex) {
            // Manejo de excepción personalizada si no se encuentra el documento
            throw new NotFoundException("Documento no encontrado: " + ex.getMessage());
        } catch (DataException ex) {
            // Manejo de excepción para errores de datos
            throw new DataException("Error al actualizar el documento: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Ocurrió un error al actualizar el documento.");
        }
    }

    // Endpoint para eliminar un documento por su ID
    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            // Elimina el documento a través del servicio usando su ID
            dS.eliminarDocumento(id);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Documento eliminado con éxito.");
        } catch (NotFoundException ex) {
            // Manejo de excepción si no se encuentra el documento a eliminar
            throw new NotFoundException("Documento no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Ocurrió un error al eliminar el documento.");
        }
    }

    // Endpoint para listar todos los documentos
    @GetMapping("/ListarTodo")
    public ResponseEntity<List<DocumentoDTO>> listarTodo() {
        try {
            // Llama al servicio para obtener una lista de todos los documentos
            List<DocumentoDTO> comentarios = dS.listarDocumentos();
            // Retorna una respuesta HTTP 200 con la lista de documentos
            return ResponseEntity.ok(comentarios);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Ocurrió un error al listar los documentos.");
        }
    }

    // Endpoint para obtener documentos filtrados por mes y año
    @GetMapping("/por-mes-anio")
    public ResponseEntity<List<DocumentoPostulante>> DocumentosPorMesYAnio(@RequestParam int anio, @RequestParam int mes) {
        try {
            // Llama al servicio para obtener documentos filtrados por mes y año
            List<DocumentoPostulante> documentos = dS.findDocumentosPorMesYAnio(anio, mes);
            // Retorna una respuesta HTTP 200 con la lista de documentos
            return ResponseEntity.ok(documentos);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Ocurrió un error al obtener los documentos por mes y año.");
        }
    }

    // Endpoint para obtener documentos filtrados solo por año
    @GetMapping("/por-anio")
    public ResponseEntity<List<DocumentoPostulante>> DocumentosPorAnio(@RequestParam int anio) {
        try {
            // Llama al servicio para obtener documentos filtrados solo por año
            List<DocumentoPostulante> documentos = dS.findDocumentosPorAnio(anio);
            // Retorna una respuesta HTTP 200 con la lista de documentos
            return ResponseEntity.ok(documentos);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Ocurrió un error al obtener los documentos por año.");
        }
    }
}
