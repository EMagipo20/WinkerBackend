package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.TipoTrabajoDTO;
import com.upc.winkerbackend.Entities.TipoTrabajo;
import com.upc.winkerbackend.ServiceImplements.TipoTrabajoServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TipoTrabajo")
@PreAuthorize("hasAnyAuthority('EMPRESA')")
public class TipoTrabajoController {
    @Autowired
    private TipoTrabajoServiceImplements tS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody TipoTrabajoDTO tipoTrabajoDTO) {
        TipoTrabajo tipoTrabajo = modelMapper.map(tipoTrabajoDTO, TipoTrabajo.class);
        tS.insertarTipoTrabajo(tipoTrabajo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody TipoTrabajoDTO tipoTrabajoDTO) {
        TipoTrabajo tipoTrabajo = modelMapper.map(tipoTrabajoDTO, TipoTrabajo.class);
        tS.actualizarTipoTrabajo(tipoTrabajo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        tS.eliminarTipoTrabajo(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<TipoTrabajoDTO>> listarTodo() {
        List<TipoTrabajoDTO> tipoTrabajo = tS.listarTipoTrabajos();
        return ResponseEntity.ok(tipoTrabajo);
    }
}