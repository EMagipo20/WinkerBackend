package com.upc.winkerbackend.Controllers;

// Importaciones necesarias para el controlador, DTOs, entidades, servicios, excepciones, y utilidades de Spring y ModelMapper
import com.upc.winkerbackend.DTO.PostDTO;
import com.upc.winkerbackend.Entities.Post;
import com.upc.winkerbackend.ServiceImplements.PostServiceImplements;
import com.upc.winkerbackend.exceptions.DataException;
import com.upc.winkerbackend.exceptions.ErrorException;
import com.upc.winkerbackend.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Define esta clase como un controlador REST
@RequestMapping("/Posts") // Establece la ruta base para todos los endpoints relacionados con posts
@PreAuthorize("hasAnyAuthority('POSTULANTE','EMPRESA')") // Restringe el acceso a usuarios con las autoridades 'POSTULANTE' o 'EMPRESA'
public class PostController {

    @Autowired // Inyección automática del servicio de post
    private PostServiceImplements postService;

    @Autowired // Inyección de ModelMapper para convertir entre DTOs y entidades
    private ModelMapper modelMapper;

    // Endpoint para agregar un nuevo post
    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody PostDTO postDTO) {
        try {
            // Convierte el DTO de Post a una entidad Post
            Post post = modelMapper.map(postDTO, Post.class);
            // Llama al servicio para insertar el post
            postService.insertarPost(post);
            // Respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Post agregado con éxito.");
        } catch (DataException ex) {
            // Maneja una excepción específica de datos
            throw new DataException("Error al agregar el post: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error interno al agregar post.");
        }
    }

    // Endpoint para actualizar un post existente
    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody PostDTO postDTO) {
        try {
            // Convierte el DTO a una entidad de tipo Post
            Post post = modelMapper.map(postDTO, Post.class);
            // Llama al servicio para actualizar el post
            postService.actualizarPost(post);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Post actualizado con éxito.");
        } catch (NotFoundException ex) {
            // Maneja excepción si no se encuentra el post
            throw new NotFoundException("Post no encontrado para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            // Manejo de excepción por errores de datos
            throw new DataException("Error al actualizar el post: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error al actualizar post.");
        }
    }

    // Endpoint para eliminar un post por su ID
    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            // Llama al servicio para eliminar el post
            postService.eliminarPost(id);
            // Retorna una respuesta HTTP 200 con un mensaje de éxito
            return ResponseEntity.ok("Post eliminado con éxito.");
        } catch (NotFoundException ex) {
            // Maneja excepción si no se encuentra el post
            throw new NotFoundException("Post no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción
            throw new ErrorException("Error al eliminar post.");
        }
    }

    // Endpoint para listar todos los posts
    @GetMapping("/ListarTodo")
    public ResponseEntity<List<PostDTO>> listarTodo() {
        try {
            // Llama al servicio para listar todos los posts y devolverlos como DTOs
            List<PostDTO> posts = postService.listarPosts();
            // Retorna la lista de posts con una respuesta HTTP 200
            return ResponseEntity.ok(posts);
        } catch (Exception ex) {
            // Manejo de cualquier excepción al listar posts
            throw new ErrorException("Error al listar posts.");
        }
    }

    // Endpoint para contar el número de posts por oferta de empleo
    @GetMapping("/count-por-oferta-empleo")
    public ResponseEntity<List<Object[]>> CountPostsPorOfertaEmpleo() {
        try {
            // Llama al servicio para contar los posts por oferta de empleo
            List<Object[]> result = postService.countPostsPorOfertaEmpleo();
            // Retorna el conteo con una respuesta HTTP 200
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            // Manejo de cualquier excepción al contar posts
            throw new ErrorException("Error al contar posts por oferta de empleo.");
        }
    }
}
