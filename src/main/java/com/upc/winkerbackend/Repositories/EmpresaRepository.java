package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    // BUSCAR A UNA EMPRESA POR RUC
    List<Empresa> findByRuc(String ruc);

    // BUSCAR A UNA EMPRESA POR NOMBRE
    List<Empresa> findByNombreEmpresa(String nombreEmpresa);

    // FILTRAR TODAS LAS EMPRESAS INSCRITAS
    @Query("SELECT COUNT(e) FROM Empresa e")
    long countTotalEmpresas();

    // FILTRAR NOMBRE DE EMPRESA MEDIANTE USERNAME
    @Query("SELECT e.nombreEmpresa FROM Empresa e JOIN e.usuario u WHERE u.username = :username")
    String findNombreEmpresaByUsuarioUsername(@Param("username") String username);
}