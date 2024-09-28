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
@Table(name ="empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre_empresa", length = 50)
    private String nombreEmpresa;

    @Column(name = "RUC", length = 11)
    private String ruc;

    @Column(name = "Telefono", length = 9)
    private String telefono;

    @Column(name = "Logo-empresa")
    private byte[] logotipo;


    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_rubro")
    private Rubro rubro;

    @OneToMany(mappedBy = "empresa")
    private List<OfertaEmpleo> ofertasEmpleo;
}