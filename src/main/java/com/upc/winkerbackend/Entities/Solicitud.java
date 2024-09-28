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
@Table(name ="solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Fecha-solicitud")
    private LocalDate fechaSolicitud;

    @Column(name = "Estado-solicitud", length = 20)
    private String estadoSolicitud;

    @Column(name = "Titulo-oferta", length = 50)
    private String tituloOferta;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_ofertaEmpleo")
    private OfertaEmpleo ofertaEmpleo;

    @ManyToOne
    @JoinColumn(name = "id_postulante")
    private Postulante postulante;
}
