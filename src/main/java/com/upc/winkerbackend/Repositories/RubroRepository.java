package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.Rubro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RubroRepository extends JpaRepository<Rubro, Long> {
    Rubro findByNombreRubro(String nombreRubro);

    @Query("SELECT COUNT(r.id) FROM Rubro r")
    Long countAllRubros();
}
