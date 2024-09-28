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
@Table(name ="postulantes")
public class Postulante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NombreCompleto", length = 100)
    private String nombreCompleto;

    @Column(name = "Email", length = 100)
    private String correo;

    @Column(name = "Telefono", length = 9)
    private String telefono;

    @Column(name = "Foto")
    private byte[] foto;

    @Column(name = "Fecha-Nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "Direccion", length = 100)
    private String direccion;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "postulante")
    private List<DocumentoPostulante> documentos;

    @OneToMany(mappedBy = "postulante")
    private List<OfertaFavorita> ofertasFavoritas;

    @OneToMany(mappedBy = "postulante")
    private List<Solicitud> solicitudes;
}
