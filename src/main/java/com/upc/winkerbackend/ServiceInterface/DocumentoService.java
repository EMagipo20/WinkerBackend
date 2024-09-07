package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.DocumentoDTO;
import com.upc.winkerbackend.Entities.DocumentoPostulante;

import java.util.List;

public interface DocumentoService {
    DocumentoPostulante insertarDocumento (DocumentoDTO documentoDTO);
    public void actualizarDocumento (DocumentoPostulante documentoPostulante);
    public void eliminarDocumento (Long id);
    public List<DocumentoDTO> listarDocumentos();
    List<DocumentoPostulante> findDocumentosPorMesYAnio(int anio, int mes);
    List<DocumentoPostulante> findDocumentosPorAnio(int anio);
}
