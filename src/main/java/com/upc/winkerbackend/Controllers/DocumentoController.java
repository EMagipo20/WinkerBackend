package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.DocumentoDTO;
import com.upc.winkerbackend.Entities.DocumentoPostulante;
import com.upc.winkerbackend.ServiceImplements.DocumentoImplements;
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
        dS.insertarDocumento(documentoDTO);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody DocumentoDTO documentoDTO) {
        ModelMapper modelMapper = new ModelMapper();
        DocumentoPostulante documentoPostulante = modelMapper.map(documentoDTO, DocumentoPostulante.class);
        dS.actualizarDocumento(documentoPostulante);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        dS.eliminarDocumento(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<DocumentoDTO>> listarTodo() {
        List<DocumentoDTO> comentarios = dS.listarDocumentos();
        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/por-mes-anio")
    public ResponseEntity<List<DocumentoPostulante>> getDocumentosPorMesYAnio(@RequestParam int anio, @RequestParam int mes) {
        List<DocumentoPostulante> documentos = dS.findDocumentosPorMesYAnio(anio, mes);
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/por-anio")
    public ResponseEntity<List<DocumentoPostulante>> getDocumentosPorAnio(@RequestParam int anio) {
        List<DocumentoPostulante> documentos = dS.findDocumentosPorAnio(anio);
        return ResponseEntity.ok(documentos);
    }
}