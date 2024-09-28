package com.upc.winkerbackend.Controllers;

// Importaciones necesarias para manejar DTOs, entidades, servicios, excepciones y utilidades de Spring y ModelMapper
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

@RestController // Anotación que marca la clase como un controlador REST
@RequestMapping("/Postulantes") // Define el endpoint base para los servicios relacionados a Postulantes
@PreAuthorize("hasAnyAuthority('POSTULANTE')") // Restringe el acceso a usuarios con la autoridad 'POSTULANTE'
public class PostulanteController {

    @Autowired // Inyección automática del servicio de Usuario
    private UsuarioServiceImplements uS;

    @Autowired // Inyección automática del servicio de Postulante
    private PostulanteServiceImplements pS;

    @Autowired // Inyección de ModelMapper para convertir entre DTOs y entidades
    private ModelMapper modelMapper;

    // Endpoint para agregar un nuevo postulante
    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody PostulanteDTO postulanteDTO) {
        try {
            // Llama al servicio para insertar el nuevo postulante
            pS.insertarPostulante(postulanteDTO);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Postulante agregado con éxito.");
        } catch (DataException ex) {
            // Manejo de excepción específica de datos
            throw new DataException("Error al agregar postulante: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al agregar postulante.");
        }
    }

    // Endpoint para actualizar un postulante existente
    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody PostulanteDTO postulanteDTO) {
        try {
            // Convierte el DTO en una entidad de tipo Postulante
            Postulante postulante = modelMapper.map(postulanteDTO, Postulante.class);
            // Llama al servicio para actualizar el postulante
            pS.actualizarPostulante(postulante);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Postulante actualizado con éxito.");
        } catch (NotFoundException ex) {
            // Manejo de excepción si el postulante no es encontrado
            throw new NotFoundException("Postulante no encontrado para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            // Manejo de excepción para errores de datos
            throw new DataException("Error al actualizar postulante: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al actualizar postulante.");
        }
    }

    // Endpoint para eliminar un postulante por su ID
    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            // Llama al servicio para eliminar el postulante
            pS.eliminarPostulante(id);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Postulante eliminado con éxito.");
        } catch (NotFoundException ex) {
            // Manejo de excepción si el postulante no es encontrado
            throw new NotFoundException("Postulante no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al eliminar postulante.");
        }
    }

    // Endpoint para listar todos los postulantes
    @GetMapping("/ListarTodo")
    public ResponseEntity<List<PostulanteDTO>> listarTodo() {
        try {
            // Llama al servicio para listar todos los postulantes
            List<PostulanteDTO> postulantes = pS.listarTodo();
            // Retorna la lista de postulantes con una respuesta HTTP 200
            return ResponseEntity.ok(postulantes);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Error interno al listar postulantes.");
        }
    }

    // Endpoint para obtener un postulante por su ID
    @GetMapping("/Obtener/{id}")
    public ResponseEntity<PostulanteDTO> obtenerPorId(@PathVariable("id") Long id) {
        try {
            // Llama al servicio para obtener un postulante por su ID
            PostulanteDTO postulanteDTO = pS.obtenerPostulantePorId(id);
            if (postulanteDTO == null) {
                throw new NotFoundException("Postulante no encontrado.");
            }
            // Retorna el postulante si es encontrado
            return ResponseEntity.ok(postulanteDTO);
        } catch (NotFoundException ex) {
            // Retorna una respuesta HTTP 404 si no se encuentra el postulante
            return ResponseEntity.status(404).body(null);
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al obtener postulante por ID.");
        }
    }

    // Endpoint para buscar postulantes por su nombre completo
    @GetMapping("/BuscarPorNombre/{nombre}")
    public ResponseEntity<List<PostulanteDTO>> buscarPorNombre(@PathVariable("nombre") String nombreCompleto) {
        try {
            // Llama al servicio para buscar postulantes por su nombre
            List<PostulanteDTO> postulantes = pS.buscarPostulantePorNombre(nombreCompleto);
            // Retorna la lista de postulantes con una respuesta HTTP 200
            return ResponseEntity.ok(postulantes);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Error interno al buscar postulantes por nombre.");
        }
    }

    // Endpoint para buscar postulantes por correo electrónico
    @GetMapping("/BuscarPorCorreo/{correo}")
    public ResponseEntity<List<PostulanteDTO>> buscarPorCorreo(@PathVariable("correo") String correo) {
        try {
            // Llama al servicio para buscar postulantes por su correo electrónico
            List<PostulanteDTO> postulantes = pS.buscarPostulantePorCorreo(correo);
            // Retorna la lista de postulantes con una respuesta HTTP 200
            return ResponseEntity.ok(postulantes);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Error interno al buscar postulantes por correo.");
        }
    }

    // Endpoint para obtener postulantes con solicitudes aceptadas
    @GetMapping("/SolicitudesAceptadas")
    public ResponseEntity<List<PostulanteDTO>> postulantesConSolicitudesAceptadas() {
        try {
            // Llama al servicio para obtener postulantes con solicitudes aceptadas
            List<PostulanteDTO> postulantes = pS.postulantesConSolicitudesAceptadas();
            // Retorna la lista de postulantes con una respuesta HTTP 200
            return ResponseEntity.ok(postulantes);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Error interno al obtener postulantes con solicitudes aceptadas.");
        }
    }

    // Endpoint para obtener el ID de un postulante basado en su nombre de usuario
    @GetMapping("/obtenerId/{username}")
    public ResponseEntity<Long> PostulanteIdByUsername(@PathVariable String username) {
        try {
            // Llama al servicio para obtener el ID de un postulante por su nombre de usuario
            Long postulanteId = pS.findPostulanteIdByUsername(username);
            if (postulanteId == null) {
                throw new NotFoundException("Postulante no encontrado.");
            }
            // Retorna el ID si es encontrado
            return ResponseEntity.ok(postulanteId);
        } catch (NotFoundException ex) {
            // Retorna una respuesta HTTP 404 si no se encuentra el postulante
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al obtener ID de postulante por nombre de usuario.");
        }
    }

    // Endpoint para contar el número de posts asociados a cada postulante
    @GetMapping("/count-posts")
    public ResponseEntity<List<Object[]>> getCountPostsPorPostulante() {
        try {
            // Llama al servicio para contar los posts por postulante
            List<Object[]> result = pS.countPostsPorPostulante();
            // Retorna la lista de conteos de posts por postulante con una respuesta HTTP 200
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            // Manejo de cualquier excepción
            throw new ErrorException("Error interno al contar posts por postulante.");
        }
    }
}
