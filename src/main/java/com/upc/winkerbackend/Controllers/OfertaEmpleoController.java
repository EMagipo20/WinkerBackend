package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.OfertaEmpleoDTO;
import com.upc.winkerbackend.Entities.OfertaEmpleo;
import com.upc.winkerbackend.ServiceImplements.OfertaEmpleoServiceImplements;
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
@RequestMapping("/OfertasEmpleo")
@PreAuthorize("hasAnyAuthority('EMPRESA', 'POSTULANTE')")
public class OfertaEmpleoController {
    @Autowired
    private OfertaEmpleoServiceImplements oES;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody OfertaEmpleoDTO ofertaEmpleoDTO) {
        try {
            OfertaEmpleo ofertaEmpleo = modelMapper.map(ofertaEmpleoDTO, OfertaEmpleo.class);
            oES.insertarOferta(ofertaEmpleo);
            return ResponseEntity.ok("Oferta de empleo agregada con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar la oferta de empleo: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al agregar oferta de empleo.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody OfertaEmpleoDTO ofertaEmpleoDTO) {
        try {
            OfertaEmpleo ofertaEmpleo = modelMapper.map(ofertaEmpleoDTO, OfertaEmpleo.class);
            oES.actualizarOferta(ofertaEmpleo);
            return ResponseEntity.ok("Oferta de empleo actualizada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Oferta de empleo no encontrada para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar la oferta de empleo: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al actualizar la oferta de empleo.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            oES.eliminarOferta(id);
            return ResponseEntity.ok("Oferta de empleo eliminada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Oferta de empleo no encontrada para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al eliminar la oferta de empleo.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<OfertaEmpleoDTO>> listarTodo() {
        try {
            List<OfertaEmpleoDTO> ofertas = oES.listarOfertasEmpleo();
            return ResponseEntity.ok(ofertas);
        } catch (Exception ex) {
            throw new ErrorException("Error al listar las ofertas de empleo.");
        }
    }

    @GetMapping("/BuscarPorTitulo/{titulo}")
    public ResponseEntity<List<OfertaEmpleoDTO>> buscarPorTitulo(@PathVariable("titulo") String titulo) {
        try {
            List<OfertaEmpleoDTO> ofertas = oES.buscarOfertasPorTitulo(titulo);
            return ResponseEntity.ok(ofertas);
        } catch (Exception ex) {
            throw new ErrorException("Error al buscar ofertas por título.");
        }
    }

    @GetMapping("/MejoresSalarios")
    public ResponseEntity<List<OfertaEmpleoDTO>> mejoresSalarios() {
        try {
            List<OfertaEmpleoDTO> ofertas = oES.ofertasConMejoresSalarios();
            return ResponseEntity.ok(ofertas);
        } catch (Exception ex) {
            throw new ErrorException("Error al buscar ofertas con mejores salarios.");
        }
    }

    @GetMapping("/ContarPorMesYAno")
    public ResponseEntity<Long> contarPorMesYAno(@RequestParam int mes, @RequestParam int ano) {
        try {
            long count = oES.contarOfertasPorMesYAno(mes, ano);
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            throw new ErrorException("Error al contar ofertas por mes y año.");
        }
    }
}
