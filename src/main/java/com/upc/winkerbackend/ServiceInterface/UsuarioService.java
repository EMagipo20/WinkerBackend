package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.Entities.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario insert(Usuario usuario);

    public List<Usuario> list();

    public void delete(int idUsuario);

    public Usuario listarId(int idUsuario);

    Usuario findByUsername(String username);

    boolean existsByUsername(String username);
}
