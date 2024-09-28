package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.OfertaEmpleoDTO;
import com.upc.winkerbackend.Entities.OfertaEmpleo;
import com.upc.winkerbackend.Repositories.OfertaEmpleoRepository;
import com.upc.winkerbackend.ServiceInterface.OfertaEmpleoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfertaEmpleoServiceImplements implements OfertaEmpleoService {
    @Autowired
    private OfertaEmpleoRepository oR;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertarOferta (OfertaEmpleo ofertaEmpleo) { oR.save(ofertaEmpleo); }

    @Override
    public void actualizarOferta(OfertaEmpleo ofertaEmpleo) {
        oR.save(ofertaEmpleo);
    }

    @Override
    public void eliminarOferta(Long id) {
        oR.deleteById(id);
    }

    @Override
    public List<OfertaEmpleoDTO> listarOfertasEmpleo() {
        return oR.findAll().stream()
                .map(ofertaEmpleo-> modelMapper.map(ofertaEmpleo, OfertaEmpleoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfertaEmpleoDTO> buscarOfertasPorTitulo(String titulo) {
        return oR.findByTituloTrabajo(titulo).stream()
                .map(ofertaEmpleo -> modelMapper.map(ofertaEmpleo, OfertaEmpleoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfertaEmpleoDTO> ofertasConMejoresSalarios() {
        return oR.findTopByOrderBySalarioDesc().stream()
                .map(ofertaEmpleo -> modelMapper.map(ofertaEmpleo, OfertaEmpleoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long contarOfertasPorMesYAno(int mes, int ano) {
        return oR.countOfertasEmpleoByMesYAno(mes, ano);
    }
}
