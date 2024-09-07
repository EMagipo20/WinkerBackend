package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.RubroDTO;
import com.upc.winkerbackend.Entities.Rubro;
import com.upc.winkerbackend.ServiceImplements.RubroServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Rubros")
@PreAuthorize("hasAnyAuthority('EMPRESA')")
public class RubroController {
    @Autowired
    private RubroServiceImplements rS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody RubroDTO rubroDTO) {
        Rubro rubro = modelMapper.map(rubroDTO, Rubro.class);
        rS.insertarRubro(rubro);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody RubroDTO rubroDTO) {
        Rubro rubro = modelMapper.map(rubroDTO, Rubro.class);
        rS.actualizarRubro(rubro);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        rS.eliminarRubro(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<RubroDTO>> listarTodo() {
        List<RubroDTO> rubro = rS.listarRubros();
        return ResponseEntity.ok(rubro);
    }

    @GetMapping("/ContarRubros")
    public ResponseEntity<Long> getCountAllRubros() {
        Long count = rS.countAllRubros();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/nombre")
    public ResponseEntity<Rubro> getByNombre(@RequestParam String nombre) {
        Rubro rubro = rS.findByNombreRubro(nombre);
        return ResponseEntity.ok(rubro);
    }
}
