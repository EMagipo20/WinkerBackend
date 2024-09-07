package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.UbicacionOferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbicacionOfertaRepository extends JpaRepository<UbicacionOferta, Long> {
    List<UbicacionOferta> findByDistrito(String distrito);
    List<UbicacionOferta> findByDepartamento(String departamento);
    List<UbicacionOferta> findByDireccion(String direccion);
}
