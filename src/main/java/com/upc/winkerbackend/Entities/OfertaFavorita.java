package com.upc.winkerbackend.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="ofertas-favoritas")
public class OfertaFavorita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_ofertaEmpleo")
    private OfertaEmpleo ofertaEmpleo;

    @ManyToOne
    @JoinColumn(name = "id_postulante")
    private Postulante postulante;
}
