package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.PostDTO;
import com.upc.winkerbackend.Entities.Post;

import java.util.List;

public interface PostService {
    public void insertarPost (Post post);
    public void actualizarPost (Post post);
    public void eliminarPost (Long id);
    List<PostDTO> listarPosts();
    List<Object[]> countPostsPorOfertaEmpleo();
}
