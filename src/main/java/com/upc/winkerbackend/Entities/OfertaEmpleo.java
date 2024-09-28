package com.upc.winkerbackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="ofertas-empleo")
public class OfertaEmpleo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Titulo-trabajo", length = 50)
    private String tituloTrabajo;

    @Column(name = "Descripcion", length = 150)
    private String descripcion;

    @Column(name = "Salario")
    private Double salario;

    @Column(name = "Fecha-publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "Fecha-vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "¿Activo?")
    private boolean indActivo;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "id_tipoTrabajo")
    private TipoTrabajo tipoTrabajo;

    @ManyToOne
    @JoinColumn(name = "id_ubicacionOferta")
    private UbicacionOferta ubicacionOferta;

    @OneToMany(mappedBy = "ofertaEmpleo")
    private List<OfertaFavorita> ofertasFavoritas;

    @OneToMany(mappedBy = "ofertaEmpleo")
    private List<Solicitud> solicitudes;
}
