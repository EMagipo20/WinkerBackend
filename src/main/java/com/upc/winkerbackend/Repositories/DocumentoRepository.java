package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.DocumentoPostulante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<DocumentoPostulante, Long> {
    @Query("SELECT d FROM DocumentoPostulante d WHERE YEAR(d.fechaCarga) = :anio AND MONTH(d.fechaCarga) = :mes")
    List<DocumentoPostulante> findDocumentosPorMesYAnio(@Param("anio") int anio, @Param("mes") int mes);

    @Query("SELECT d FROM DocumentoPostulante d WHERE YEAR(d.fechaCarga) = :anio")
    List<DocumentoPostulante> findDocumentosPorAnio(@Param("anio") int anio);

    @Query("SELECT d.postulante.id, COUNT(d.id) FROM DocumentoPostulante d WHERE d.postulante.id = :postulanteId GROUP BY d.postulante.id")
    Long countDocumentosPorPostulante(@Param("postulanteId") Long postulanteId);
}
