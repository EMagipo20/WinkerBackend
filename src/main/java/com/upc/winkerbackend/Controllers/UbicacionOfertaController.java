package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.UbicacionOfertaDTO;
import com.upc.winkerbackend.Entities.UbicacionOferta;
import com.upc.winkerbackend.ServiceImplements.UbicacionServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/UbicacionOferta")
@PreAuthorize("hasAnyAuthority('EMPRESA')")
public class UbicacionOfertaController {
    @Autowired
    private UbicacionServiceImplements uOS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody UbicacionOfertaDTO ubicacionOfertaDTO) {
        UbicacionOferta ubicacionOferta = modelMapper.map(ubicacionOfertaDTO, UbicacionOferta.class);
        uOS.insertarUbicacion(ubicacionOferta);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody UbicacionOfertaDTO ubicacionOfertaDTO) {
        UbicacionOferta ubicacionOferta = modelMapper.map(ubicacionOfertaDTO, UbicacionOferta.class);
        uOS.actualizarUbicacion(ubicacionOferta);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        uOS.eliminarUbicacion(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<UbicacionOfertaDTO>> listarTodo() {
        List<UbicacionOfertaDTO> ubicacionOferta = uOS.listarUbicaciones();
        return ResponseEntity.ok(ubicacionOferta);
    }

    @GetMapping("/distrito")
    public ResponseEntity<List<UbicacionOferta>> getByDistrito(@RequestParam String distrito) {
        List<UbicacionOferta> ubicaciones = uOS.findByDistrito(distrito);
        return ResponseEntity.ok(ubicaciones);
    }

    @GetMapping("/departamento")
    public ResponseEntity<List<UbicacionOferta>> getByDepartamento(@RequestParam String departamento) {
        List<UbicacionOferta> ubicaciones = uOS.findByDepartamento(departamento);
        return ResponseEntity.ok(ubicaciones);
    }

    @GetMapping("/direccion")
    public ResponseEntity<List<UbicacionOferta>> getByDireccion(@RequestParam String direccion) {
        List<UbicacionOferta> ubicaciones = uOS.findByDireccion(direccion);
        return ResponseEntity.ok(ubicaciones);
    }
}