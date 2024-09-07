package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.EmpresaDTO;
import com.upc.winkerbackend.DTO.OfertaFavoritaDTO;
import com.upc.winkerbackend.Entities.OfertaFavorita;
import com.upc.winkerbackend.ServiceImplements.OfertaFavoritaServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/OfertasFavoritas")
@PreAuthorize("hasAnyAuthority('POSTULANTE')")
public class OfertaFavoritaController {
    @Autowired
    private OfertaFavoritaServiceImplements ofertaFavoritaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody OfertaFavoritaDTO ofertaFavoritaDTO) {
        OfertaFavorita ofertaFavorita = modelMapper.map(ofertaFavoritaDTO, OfertaFavorita.class);
        ofertaFavoritaService.insertarFavorita(ofertaFavorita);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        ofertaFavoritaService.eliminarFavorita(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<OfertaFavoritaDTO>> listarTodo() {
        List<OfertaFavoritaDTO> favoritas = ofertaFavoritaService.listarFavoritas();
        return ResponseEntity.ok(favoritas);
    }

    @GetMapping("/ContarFavoritas")
    public ResponseEntity<Long> contarFavoritas() {
        long count = ofertaFavoritaService.contarOfertasFavoritas();
        return ResponseEntity.ok(count);
    }
}