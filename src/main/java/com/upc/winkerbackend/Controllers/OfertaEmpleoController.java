package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.OfertaEmpleoDTO;
import com.upc.winkerbackend.Entities.OfertaEmpleo;
import com.upc.winkerbackend.ServiceImplements.OfertaEmpleoServiceImplements;
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
        OfertaEmpleo ofertaEmpleo = modelMapper.map(ofertaEmpleoDTO, OfertaEmpleo.class);
        oES.insertarOferta(ofertaEmpleo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody OfertaEmpleoDTO ofertaEmpleoDTO) {
        OfertaEmpleo ofertaEmpleo = modelMapper.map(ofertaEmpleoDTO, OfertaEmpleo.class);
        oES.actualizarOferta(ofertaEmpleo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        oES.eliminarOferta(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<OfertaEmpleoDTO>> listarTodo() {
        List<OfertaEmpleoDTO> ofertas = oES.listarOfertasEmpleo();
        return ResponseEntity.ok(ofertas);
    }

    @GetMapping("/BuscarPorTitulo/{titulo}")
    public ResponseEntity<List<OfertaEmpleoDTO>> buscarPorTitulo(@PathVariable("titulo") String titulo) {
        List<OfertaEmpleoDTO> ofertas = oES.buscarOfertasPorTitulo(titulo);
        return ResponseEntity.ok(ofertas);
    }

    @GetMapping("/MejoresSalarios")
    public ResponseEntity<List<OfertaEmpleoDTO>> mejoresSalarios() {
        List<OfertaEmpleoDTO> ofertas = oES.ofertasConMejoresSalarios();
        return ResponseEntity.ok(ofertas);
    }

    @GetMapping("/ContarPorMesYAno")
    public ResponseEntity<Long> contarPorMesYAno(@RequestParam int mes, @RequestParam int ano) {
        long count = oES.contarOfertasPorMesYAno(mes, ano);
        return ResponseEntity.ok(count);
    }
}
