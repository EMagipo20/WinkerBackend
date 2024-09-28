package com.upc.winkerbackend.Controllers;

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

@RestController
@RequestMapping("/Posts")
@PreAuthorize("hasAnyAuthority('POSTULANTE','EMPRESA')")
public class PostController {
    @Autowired
    private PostServiceImplements postService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody PostDTO postDTO) {
        try {
            Post post = modelMapper.map(postDTO, Post.class);
            postService.insertarPost(post);
            return ResponseEntity.ok("Post agregado con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar el post: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error interno al agregar post.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody PostDTO postDTO) {
        try {
            Post post = modelMapper.map(postDTO, Post.class);
            postService.actualizarPost(post);
            return ResponseEntity.ok("Post actualizado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Post no encontrado para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar el post: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al actualizar post.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            postService.eliminarPost(id);
            return ResponseEntity.ok("Post eliminado con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Post no encontrado para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al eliminar post.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<PostDTO>> listarTodo() {
        try {
            List<PostDTO> posts = postService.listarPosts();
            return ResponseEntity.ok(posts);
        } catch (Exception ex) {
            throw new ErrorException("Error al listar posts.");
        }
    }

    @GetMapping("/count-por-oferta-empleo")
    public ResponseEntity<List<Object[]>> CountPostsPorOfertaEmpleo() {
        try {
            List<Object[]> result = postService.countPostsPorOfertaEmpleo();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            throw new ErrorException("Error al contar posts por oferta de empleo.");
        }
    }
}