package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.DocumentoDTO;
import com.upc.winkerbackend.Entities.DocumentoPostulante;
import com.upc.winkerbackend.Entities.Postulante;
import com.upc.winkerbackend.Repositories.DocumentoRepository;
import com.upc.winkerbackend.Repositories.PostulanteRepository;
import com.upc.winkerbackend.ServiceInterface.DocumentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoImplements implements DocumentoService {
    @Autowired
    private DocumentoRepository dR;

    @Autowired
    private PostulanteRepository pR;

    @Value("${app.max-archivo-size}")
    private int maxArchivoSize;

    private static final byte[] PDF_HEADER = {0x25, 0x50, 0x44, 0x46};

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DocumentoPostulante insertarDocumento(DocumentoDTO documentoDTO) {
        DocumentoPostulante documentoPostulante = new DocumentoPostulante();
        documentoPostulante.setTipo(documentoDTO.getTipo());
        documentoPostulante.setFechaCarga(documentoDTO.getFechaCarga());

        if (documentoDTO.getArchivoBase64() != null) {
            byte[] archivoBytes = Base64.getDecoder().decode(documentoDTO.getArchivoBase64());
            if (archivoBytes.length > maxArchivoSize) {
                throw new IllegalArgumentException("El tamaño del Archivo excede el límite máximo permitido de 5MB");
            }
            if (!isValidFormat(archivoBytes, PDF_HEADER)) {
                throw new IllegalArgumentException("Formato del Archivo es inválido. Solo se acepta PDF");
            }
            documentoPostulante.setArchivo(archivoBytes);
        }
        // Agregar el usuario asociado
        Postulante postulante = pR.findById(documentoDTO.getPostulanteId())
                .orElseThrow(() -> new RuntimeException("Postulante no encontrado"));
        documentoPostulante.setPostulante(postulante);
        return dR.save(documentoPostulante);
    }

    private boolean isValidFormat(byte[] fileBytes, byte[]... headers) {
        for (byte[] header : headers) {
            if (fileBytes.length >= header.length && Arrays.equals(Arrays.copyOf(fileBytes, header.length), header)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actualizarDocumento(DocumentoPostulante documentoPostulante) {
        // Recupera el documento existente desde la base de datos
        DocumentoPostulante documentoExistente = dR.findById(documentoPostulante.getId())
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        // Copia todos los campos excepto el archivo de la entidad actualizada
        documentoExistente.setTipo(documentoPostulante.getTipo());
        documentoExistente.setFechaCarga(documentoPostulante.getFechaCarga());
        // No se actualiza el archivo

        // Guarda la entidad actualizada sin modificar el archivo
        dR.save(documentoExistente);
    }

    @Override
    public void eliminarDocumento(Long id) {
        dR.deleteById(id);
    }

    @Override
    public List<DocumentoDTO> listarDocumentos() {
        return dR.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentoPostulante> findDocumentosPorMesYAnio(int anio, int mes) {
        return dR.findDocumentosPorMesYAnio(anio, mes);
    }

    @Override
    public List<DocumentoPostulante> findDocumentosPorAnio(int anio) {
        return dR.findDocumentosPorAnio(anio);
    }

    private DocumentoDTO convertToDTO(DocumentoPostulante documentoPostulante) {
        return modelMapper.map(documentoPostulante, DocumentoDTO.class);
    }
}
