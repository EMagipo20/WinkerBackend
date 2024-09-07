package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.EmpresaDTO;
import com.upc.winkerbackend.Entities.Empresa;

import java.util.List;

public interface EmpresaService {
    Empresa insertarEmpresa(EmpresaDTO empresaDTO);
    public void actualizarEmpresa(Empresa empresa);
    public void eliminarEmpresa(Long id);
    public List<EmpresaDTO> listarEmpresas();
    public EmpresaDTO obtenerEmpresaPorId(Long id);
    List<EmpresaDTO> buscarEmpresaPorRuc(String ruc);
    List<EmpresaDTO> buscarEmpresaPorNombre(String nombreEmpresa);
    long contarTotalEmpresas();
    String findNombreEmpresaByUsername(String username);
}
