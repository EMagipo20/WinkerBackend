package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.OfertaFavorita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaFavoritaRepository extends JpaRepository<OfertaFavorita, Long> {
    @Query("SELECT COUNT(f) FROM OfertaFavorita f")
    long countTotalFavoritas(); //CONTAR LA CANTIDAD DE OFERTAS DE EMPLEO QUE SE HAN AÑADIDO COMO FAVORITAS
}
