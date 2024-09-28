package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.OfertaFavoritaDTO;
import com.upc.winkerbackend.Entities.OfertaFavorita;
import com.upc.winkerbackend.ServiceImplements.OfertaFavoritaServiceImplements;
import com.upc.winkerbackend.exceptions.DataException;
import com.upc.winkerbackend.exceptions.ErrorException;
import com.upc.winkerbackend.exceptions.NotFoundException;
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
        try {
            OfertaFavorita ofertaFavorita = modelMapper.map(ofertaFavoritaDTO, OfertaFavorita.class);
            ofertaFavoritaService.insertarFavorita(ofertaFavorita);
            return ResponseEntity.ok("Oferta favorita agregada con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar oferta favorita: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al agregar oferta favorita.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            ofertaFavoritaService.eliminarFavorita(id);
            return ResponseEntity.ok("Oferta favorita eliminada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Oferta favorita no encontrada para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al eliminar oferta favorita.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<OfertaFavoritaDTO>> listarTodo() {
        try {
            List<OfertaFavoritaDTO> favoritas = ofertaFavoritaService.listarFavoritas();
            return ResponseEntity.ok(favoritas);
        } catch (Exception ex) {
            throw new ErrorException("Error al listar ofertas favoritas.");
        }
    }

    @GetMapping("/ContarFavoritas")
    public ResponseEntity<Long> contarFavoritas() {
        try {
            long count = ofertaFavoritaService.contarOfertasFavoritas();
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            throw new ErrorException("Error al contar ofertas favoritas.");
        }
    }
}