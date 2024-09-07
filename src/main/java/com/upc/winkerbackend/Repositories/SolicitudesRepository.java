package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudesRepository extends JpaRepository<Solicitud, Long> {
    // LISTAR SOLICITUDES POR POSTULANTE
    List<Solicitud> findByPostulanteId(Long postulanteId);

    // CONTAR LA CANTIDAD DE SOLICITUDES ENVIADAS
    @Query("SELECT COUNT(s) FROM Solicitud s")
    long countTotalSolicitudes();

    // CONTAR LA CANTIDAD DE POSTULACIONES DE UN POSTULANTE
    @Query("SELECT COUNT(s) FROM Solicitud s WHERE s.postulante.id = :postulanteId")
    long countSolicitudesByPostulanteId(Long postulanteId);

    // OBTENER TODAS LAS SOLICITUDES RECIBIDAS SOBRE UNA DETERMINADA OFERTA DE EMPLEO
    @Query("SELECT s FROM Solicitud s WHERE s.ofertaEmpleo.id = :ofertaEmpleoId")
    List<Solicitud> findByOfertaEmpleoId(Long ofertaEmpleoId);

    // FILTRAR TODOS LOS POSTULANTES CUYA SOLICITUD HA SIDO ACEPTADA
    @Query("SELECT s FROM Solicitud s WHERE s.estadoSolicitud = 'Aceptada'")
    List<Solicitud> findSolicitudesAceptadas();
}
