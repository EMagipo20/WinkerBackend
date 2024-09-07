package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.TipoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTrabajoRepository extends JpaRepository<TipoTrabajo, Long> {
}
