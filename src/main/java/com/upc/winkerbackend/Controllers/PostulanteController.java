package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.PostulanteDTO;
import com.upc.winkerbackend.Entities.Postulante;
import com.upc.winkerbackend.ServiceImplements.PostulanteServiceImplements;
import com.upc.winkerbackend.ServiceImplements.UsuarioServiceImplements;
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
@RequestMapping("/Postulantes")
@PreAuthorize("hasAnyAuthority('POSTULANTE')")
public class PostulanteController {

    @Autowired
    private UsuarioServiceImplements uS;

    @Autowired
    private PostulanteServiceImplements pS;
    @Autowired

    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody PostulanteDTO postulanteDTO) {
        try {
            pS.insertarPostulante(postulanteDTO);
            return ResponseEntity.ok("Postulante agregado con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar postulante: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al agregar postulante.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody PostulanteDTO postulanteDTO) {
        try {
            Postulante postulante = modelMapper.map(postulanteDTO, Postulante.class);
            pS.actualizarPostulante(postulante);
            return ResponseEntity.ok("Postulante actualizado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Postulante no encontrado para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar postulante: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al actualizar postulante.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            pS.eliminarPostulante(id);
            return ResponseEntity.ok("Postulante eliminado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Postulante no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al eliminar postulante.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<PostulanteDTO>> listarTodo() {
        try {
            List<PostulanteDTO> postulantes = pS.listarTodo();
            return ResponseEntity.ok(postulantes);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al listar postulantes.");
        }
    }

    @GetMapping("/Obtener/{id}")
    public ResponseEntity<PostulanteDTO> obtenerPorId(@PathVariable("id") Long id) {
        try {
            PostulanteDTO postulanteDTO = pS.obtenerPostulantePorId(id);
            if (postulanteDTO == null) {
                throw new NotFoundException("Postulante no encontrado.");
            }
            return ResponseEntity.ok(postulanteDTO);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al obtener postulante por ID.");
        }
    }

    @GetMapping("/BuscarPorNombre/{nombre}")
    public ResponseEntity<List<PostulanteDTO>> buscarPorNombre(@PathVariable("nombre") String nombreCompleto) {
        try {
            List<PostulanteDTO> postulantes = pS.buscarPostulantePorNombre(nombreCompleto);
            return ResponseEntity.ok(postulantes);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al buscar postulantes por nombre.");
        }
    }

    @GetMapping("/BuscarPorCorreo/{correo}")
    public ResponseEntity<List<PostulanteDTO>> buscarPorCorreo(@PathVariable("correo") String correo) {
        try {
            List<PostulanteDTO> postulantes = pS.buscarPostulantePorCorreo(correo);
            return ResponseEntity.ok(postulantes);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al buscar postulantes por correo.");
        }
    }

    @GetMapping("/SolicitudesAceptadas")
    public ResponseEntity<List<PostulanteDTO>> postulantesConSolicitudesAceptadas() {
        try {
            List<PostulanteDTO> postulantes = pS.postulantesConSolicitudesAceptadas();
            return ResponseEntity.ok(postulantes);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al obtener postulantes con solicitudes aceptadas.");
        }
    }

    @GetMapping("/obtenerId/{username}")
    public ResponseEntity<Long> PostulanteIdByUsername(@PathVariable String username) {
        try {
            Long postulanteId = pS.findPostulanteIdByUsername(username);
            if (postulanteId == null) {
                throw new NotFoundException("Postulante no encontrado.");
            }
            return ResponseEntity.ok(postulanteId);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            throw new ErrorException("Error interno al obtener ID de postulante por nombre de usuario.");
        }
    }

    @GetMapping("/count-posts")
    public ResponseEntity<List<Object[]>> getCountPostsPorPostulante() {
        try {
            List<Object[]> result = pS.countPostsPorPostulante();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            throw new ErrorException("Error interno al contar posts por postulante.");
        }
    }
}
