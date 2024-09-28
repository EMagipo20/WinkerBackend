package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Define esta clase como un repositorio para la entidad Empresa
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    // Método para buscar empresas por RUC
    List<Empresa> findByRuc(String ruc);

    // Método para buscar empresas por el nombre
    List<Empresa> findByNombreEmpresa(String nombreEmpresa);

    // Query personalizada para contar el número total de empresas registradas
    @Query("SELECT COUNT(e) FROM Empresa e")
    long countTotalEmpresas();

    // Query personalizada para obtener el nombre de la empresa basado en el nombre de usuario de su usuario asociado
    @Query("SELECT e.nombreEmpresa FROM Empresa e JOIN e.usuario u WHERE u.username = :username")
    String findNombreEmpresaByUsuarioUsername(@Param("username") String username);
}
