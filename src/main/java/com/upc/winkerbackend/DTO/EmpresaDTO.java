package com.upc.winkerbackend.DTO;

import lombok.Data;

@Data
public class EmpresaDTO {
    private Long id;
    private String nombre;
    private String ruc;
    private String telefono;
    private String logotipoBase64;
    //FK
    private Long rubroId;
    private Integer UsuarioId;
}
