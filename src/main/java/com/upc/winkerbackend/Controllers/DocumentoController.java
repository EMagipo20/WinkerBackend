package com.upc.winkerbackend.Controllers;

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

@RestController
@RequestMapping("/Documentos")
@PreAuthorize("hasAnyAuthority('POSTULANTE')")
public class DocumentoController {
    @Autowired
    private DocumentoImplements dS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody DocumentoDTO documentoDTO) {
        try {
            dS.insertarDocumento(documentoDTO);
            return ResponseEntity.ok("Documento agregado con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar el documento: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Ocurrió un error interno al agregar el documento.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody DocumentoDTO documentoDTO) {
        try {
            DocumentoPostulante documentoPostulante = modelMapper.map(documentoDTO, DocumentoPostulante.class);
            dS.actualizarDocumento(documentoPostulante);
            return ResponseEntity.ok("Documento actualizado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Documento no encontrado: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar el documento: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Ocurrió un error al actualizar el documento.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            dS.eliminarDocumento(id);
            return ResponseEntity.ok("Documento eliminado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Documento no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Ocurrió un error al eliminar el documento.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<DocumentoDTO>> listarTodo() {
        try {
            List<DocumentoDTO> comentarios = dS.listarDocumentos();
            return ResponseEntity.ok(comentarios);
        } catch (Exception ex) {
            throw new ErrorException("Ocurrió un error al listar los documentos.");
        }
    }

    @GetMapping("/por-mes-anio")
    public ResponseEntity<List<DocumentoPostulante>> DocumentosPorMesYAnio(@RequestParam int anio, @RequestParam int mes) {
        try {
            List<DocumentoPostulante> documentos = dS.findDocumentosPorMesYAnio(anio, mes);
            return ResponseEntity.ok(documentos);
        } catch (Exception ex) {
            throw new ErrorException("Ocurrió un error al obtener los documentos por mes y año.");
        }
    }

    @GetMapping("/por-anio")
    public ResponseEntity<List<DocumentoPostulante>> DocumentosPorAnio(@RequestParam int anio) {
        try {
            List<DocumentoPostulante> documentos = dS.findDocumentosPorAnio(anio);
            return ResponseEntity.ok(documentos);
        } catch (Exception ex) {
            throw new ErrorException("Ocurrió un error al obtener los documentos por año.");
        }
    }
}