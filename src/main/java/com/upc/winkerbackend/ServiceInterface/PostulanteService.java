package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.PostulanteDTO;
import com.upc.winkerbackend.Entities.Postulante;

import java.time.LocalDate;
import java.util.List;

public interface PostulanteService {
    Postulante insertarPostulante(PostulanteDTO postulanteDTO);
    Postulante actualizarPostulante(Postulante postulante);
    List<Postulante> listarTodo();
    void eliminarPostulante(Long id);
    PostulanteDTO obtenerPostulantePorId(Long id);
    List<PostulanteDTO> buscarPostulantePorNombre(String nombreCompleto);
    List<PostulanteDTO> buscarPostulantePorCorreo(String correo);
    List<PostulanteDTO> postulantesConSolicitudesAceptadas();
    Long findPostulanteIdByUsername(String username);
    List<Object[]> countPostsPorPostulante();

    List<PostulanteDTO> buscarPostulantePorFechaNacimiento(LocalDate fechaNacimiento);
}
