package com.upc.winkerbackend.DTO;

import com.upc.winkerbackend.Entities.Usuario;

public class RolDTO {
    private Integer id;
    private String rol;
    private Usuario user;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Usuario getUser() {
        return user;
    }
    public void setUser(Usuario user) {
        this.user = user;
    }
}

