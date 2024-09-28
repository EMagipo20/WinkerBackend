package com.upc.winkerbackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name ="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "fechaRegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = new Date();
    }

    //Relaciones
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Usuario")
    private List<Rol> rol;

    @OneToMany(mappedBy = "usuario")
    private List<Postulante> postulante;

    @OneToMany(mappedBy = "usuario")
    private List<Empresa> empresa;

    public Usuario() {}

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public Boolean getEnabled() { return enabled; }

    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public List<Rol> getRoles() { return rol; }

    public void setRoles(List<Rol> roles) { this.rol = roles; }

    public Date getFechaRegistro() { return fechaRegistro; }

    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public List<Postulante> getPostulante() { return postulante; }

    public void setPostulante(List<Postulante> postulante) { this.postulante = postulante; }

    public List<Empresa> getEmpresa() { return empresa; }

    public void setEmpresa(List<Empresa> empresa) { this.empresa = empresa; }
}