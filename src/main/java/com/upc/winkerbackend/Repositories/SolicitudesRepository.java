package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Define este repositorio para la entidad Solicitud
public interface SolicitudesRepository extends JpaRepository<Solicitud, Long> {

    // Listar todas las solicitudes realizadas por un postulante en específico
    List<Solicitud> findByPostulanteId(Long postulanteId);

    // Contar el número total de solicitudes enviadas
    @Query("SELECT COUNT(s) FROM Solicitud s")
    long countTotalSolicitudes();

    // Contar la cantidad de solicitudes (postulaciones) hechas por un postulante específico
    @Query("SELECT COUNT(s) FROM Solicitud s WHERE s.postulante.id = :postulanteId")
    long countSolicitudesByPostulanteId(Long postulanteId);

    // Obtener todas las solicitudes recibidas para una determinada oferta de empleo
    @Query("SELECT s FROM Solicitud s WHERE s.ofertaEmpleo.id = :ofertaEmpleoId")
    List<Solicitud> findByOfertaEmpleoId(Long ofertaEmpleoId);

    // Filtrar todas las solicitudes que han sido aceptadas
    @Query("SELECT s FROM Solicitud s WHERE s.estadoSolicitud = 'Aceptada'")
    List<Solicitud> findSolicitudesAceptadas();
}
