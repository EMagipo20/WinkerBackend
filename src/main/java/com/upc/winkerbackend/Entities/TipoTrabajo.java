package com.upc.winkerbackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="tipo-trabajos")
public class TipoTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name= "Tipo")
    private String tipo;

    @Column(name = "Dias")
    private String dia;

    @Column(name = "Hora-inicio")
    private LocalTime horaInicio;

    @Column(name = "Hora-fin")
    private LocalTime horaFin;

    //Relaciones
    @OneToMany(mappedBy = "tipoTrabajo")
    private List<OfertaEmpleo> ofertasEmpleo;
}
