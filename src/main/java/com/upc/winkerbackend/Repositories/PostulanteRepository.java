package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.Postulante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostulanteRepository extends JpaRepository<Postulante, Long> {
    // BUSCAR A UN POSTULANTE POR SU NOMBRECOMPLETO
    List<Postulante> findByNombreCompletoContaining(String nombreCompleto);

    // BUSCAR A UN POSTULANTE POR EMAIL
    List<Postulante> findByCorreoContaining(String correo);

    // FILTRAR TODOS LOS POSTULANTES CUYA SOLICITUD HA SIDO ACEPTADA
    @Query("SELECT p FROM Postulante p JOIN p.solicitudes s WHERE s.estadoSolicitud = 'Aceptada'")
    List<Postulante> findPostulantesConSolicitudesAceptadas();

    // FILTRAR ID DE POSTULANTE MEDIANTE USERNAME
    @Query("SELECT p.id FROM Postulante p JOIN p.usuario u WHERE u.username = :username")
    Optional<Long> findPostulanteIdByUsername(@Param("username") String username);

    // CANTIDAD POSTS POR POSTULANTE
    @Query("SELECT p.postulante.id, COUNT(p.id) FROM Post p GROUP BY p.postulante.id")
    List<Object[]> countPostsPorPostulante();

    // BUSCAR A UN POSTULANTE POR SU FECHA DE NACIMIENTO
    List<Postulante> findByFechaNacimiento(LocalDate fechaNacimiento);

    // LSITAR POSTULANTES POR AÑO
    @Query("SELECT p FROM Postulante p WHERE YEAR(p.fechaNacimiento) = :anio")
    List<Postulante> FindByYear(@Param("anio") Integer anio);
}
