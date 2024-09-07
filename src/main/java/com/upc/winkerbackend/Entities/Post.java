package com.upc.winkerbackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Pregunta", length = 100)
    private String pregunta;

    @Column(name = "Respuesta", length = 200)
    private String respuesta;

    @Column(name = "Fecha-publicacion")
    private LocalDate fechaPublicacion;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_postulante")
    private Postulante postulante;

    @ManyToOne
    @JoinColumn(name = "id_ofertaEmpleo")
    private OfertaEmpleo ofertaEmpleo;
}
