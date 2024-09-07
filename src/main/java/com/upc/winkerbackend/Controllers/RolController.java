package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.RolDTO;
import com.upc.winkerbackend.Entities.Rol;
import com.upc.winkerbackend.ServiceInterface.RolService;
import com.upc.winkerbackend.ServiceInterface.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RolController {
    @Autowired
    private RolService rS;

    @Autowired
    private UsuarioService uS;

    @PostMapping("/Agregar")
    public ResponseEntity<RolDTO> registrar(@RequestBody RolDTO dto) {
        ModelMapper m = new ModelMapper();
        Rol r = m.map(dto, Rol.class);
        Rol rolGuardado = rS.insert(r);
        RolDTO responseDto = m.map(rolGuardado, RolDTO.class);
        return ResponseEntity.ok(responseDto);
    }
}
