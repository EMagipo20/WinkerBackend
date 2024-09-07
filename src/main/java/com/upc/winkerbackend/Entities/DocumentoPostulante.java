package com.upc.winkerbackend.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="documentos-postulante")
public class DocumentoPostulante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Tipo-documento", length = 50)
    private String tipo;

    @Column(name = "Fecha-carga")
    private LocalDate fechaCarga;

    @Column(name = "Documento")
    private byte[] archivo;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_postulante")
    private Postulante postulante;
}
