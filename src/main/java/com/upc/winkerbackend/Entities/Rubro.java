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
@Table(name ="rubros")
public class Rubro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "nombre-rubro", length = 30)
    private String nombreRubro;

    //Relaciones
    @OneToMany(mappedBy = "rubro")
    private List<Empresa> empresa;
}
