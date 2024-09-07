package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.PostDTO;
import com.upc.winkerbackend.Entities.Post;
import com.upc.winkerbackend.Repositories.PostRepository;
import com.upc.winkerbackend.ServiceInterface.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplements implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertarPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void actualizarPost(Post post) { postRepository.save(post); }


    @Override
    public void eliminarPost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDTO> listarPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> countPostsPorOfertaEmpleo() {
        return postRepository.countPostsPorOfertaEmpleo();
    }

    private PostDTO convertToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }
}