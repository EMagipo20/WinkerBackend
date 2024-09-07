package com.upc.winkerbackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="ubicaciones-oferta")
public class UbicacionOferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Departamento", length = 50)
    private String departamento;

    @Column(name = "Distrito", length = 50)
    private String distrito;

    @Column(name = "Direccion", length = 100)
    private String direccion;

    //Relaciones
    @OneToMany(mappedBy = "ubicacionOferta")
    private List<OfertaEmpleo> ofertasEmpleo;
}
