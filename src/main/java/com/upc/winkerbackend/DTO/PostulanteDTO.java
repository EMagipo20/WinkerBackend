package com.upc.winkerbackend.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostulanteDTO {
    public Long id;
    public String nombreCompleto;
    public String correo;
    public String telefono;
    public LocalDate fechaNacimiento;
    public String direccion;
    private String fotoBase64;
    //FK
    private Integer UsuarioId;
}
