package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.OfertaEmpleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaEmpleoRepository extends JpaRepository<OfertaEmpleo, Long> {
    // BUSCAR OFERTAS DE EMPLEO POR SU TITULO
    List<OfertaEmpleo> findByTituloTrabajo(String tituloTrabajo);

    // FILTRAR OFERTAS DE EMPLEO CON MEJORES SALARIOS
    @Query("SELECT o FROM OfertaEmpleo o ORDER BY o.salario DESC")
    List<OfertaEmpleo> findTopByOrderBySalarioDesc();

    // CONTAR LA CANTIDAD DE OFERTAS DE EMPLEO QUE SE HAN AÑADIDO COMO FAVORITAS
    @Query("SELECT COUNT(o) FROM OfertaEmpleo o JOIN o.ofertasFavoritas f WHERE f.postulante.id = :postulanteId")
    long countFavoritasByPostulanteId(Long postulanteId);

    // CANTIDAD DE OFERTAS DE EMPLEO REGISTRADAS POR MES Y AÑO
    @Query("SELECT COUNT(o) FROM OfertaEmpleo o WHERE MONTH(o.fechaPublicacion) = :mes AND YEAR(o.fechaPublicacion) = :ano")
    long countOfertasEmpleoByMesYAno(int mes, int ano);
}
