package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.PostDTO;
import com.upc.winkerbackend.Entities.Post;
import com.upc.winkerbackend.ServiceImplements.PostServiceImplements;
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

    @PostMapping("/AgregarPost")
    public ResponseEntity<String> agregar(@RequestBody PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        postService.insertarPost(post);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ActualizarPost")
    public ResponseEntity<String> actualizar(@RequestBody PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        postService.actualizarPost(post);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        postService.eliminarPost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<PostDTO>> listarTodo() {
        List<PostDTO> post = postService.listarPosts();
        return ResponseEntity.ok(post);
    }

    @GetMapping("/count-por-oferta-empleo")
    public ResponseEntity<List<Object[]>> CountPostsPorOfertaEmpleo() {
        List<Object[]> result = postService.countPostsPorOfertaEmpleo();
        return ResponseEntity.ok(result);
    }
}