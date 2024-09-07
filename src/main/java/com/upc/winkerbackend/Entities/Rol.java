package com.upc.winkerbackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name ="roles")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idRol;

    @Column(name = "rol")
    private String rol;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario user;

    public Rol() {}

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public int getId() {
        return idRol;
    }

    public void setId(int id) {
        this.idRol = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
