package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.OfertaFavoritaDTO;
import com.upc.winkerbackend.Entities.OfertaFavorita;
import com.upc.winkerbackend.Repositories.OfertaFavoritaRepository;
import com.upc.winkerbackend.ServiceInterface.OfertaFavoritaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfertaFavoritaServiceImplements implements OfertaFavoritaService {
    @Autowired
    private OfertaFavoritaRepository ofertaFavoritaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertarFavorita(OfertaFavorita ofertaFavorita) { ofertaFavoritaRepository.save(ofertaFavorita); }

    @Override
    public void eliminarFavorita (Long id) { ofertaFavoritaRepository.deleteById(id);}

    @Override
    public List<OfertaFavoritaDTO> listarFavoritas() {
        return ofertaFavoritaRepository.findAll().stream()
                .map(favorita -> modelMapper.map(favorita, OfertaFavoritaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long contarOfertasFavoritas() {
        return ofertaFavoritaRepository.countTotalFavoritas();
    }

    private OfertaFavoritaDTO convertToDTO(OfertaFavorita ofertaFavorita) {
        OfertaFavoritaDTO dto = modelMapper.map(ofertaFavorita, OfertaFavoritaDTO.class);
        return dto;
    }
}

