package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.Entities.Usuario;
import com.upc.winkerbackend.Repositories.UsuarioRepository;
import com.upc.winkerbackend.ServiceInterface.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImplements implements UsuarioService {
    @Autowired
    private UsuarioRepository uR;

    @Override
    public Usuario insert(Usuario usuario) {
        return uR.save(usuario);
    }

    @Override
    public void delete(int idUsuario) {
        uR.deleteById(idUsuario);
    }

    @Override
    public Usuario listarId(int idUsuario) {
        return uR.findById(idUsuario).orElse(new Usuario());
    }

    @Override
    public boolean existsByUsername(String username) {
        return uR.existsByUsername(username);
    }

    @Override
    public Usuario findByUsername(String username) {
        return uR.findByUsername(username);
    }

    @Override
    public List<Usuario> list() {
        return (List<Usuario>) uR.findAll();
    }
}