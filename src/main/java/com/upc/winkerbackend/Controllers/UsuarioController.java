package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.UsuarioDTO;
import com.upc.winkerbackend.Entities.Usuario;
import com.upc.winkerbackend.ServiceInterface.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService uS;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/Agregar")
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuario u = m.map(dto, Usuario.class);
        String encodedPassword = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);

        Usuario usuarioGuardado = uS.insert(u);

        UsuarioDTO responseDto = m.map(usuarioGuardado, UsuarioDTO.class);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/Eliminar/{id}")
    public void eliminar(@PathVariable("id") int id) {
        uS.delete(id);
    }

    @GetMapping("/{id}")
    public UsuarioDTO listarId(@PathVariable("id") int id) {
        ModelMapper m = new ModelMapper();
        UsuarioDTO dto = m.map(uS.listarId(id), UsuarioDTO.class);
        return dto;
    }

    @GetMapping("/ListarTodo")
    public List<UsuarioDTO> listar() {
        return uS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UsuarioDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable("username") String username) {
        boolean exists = uS.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/FindByUsername/{username}")
    public ResponseEntity<UsuarioDTO> findByUsername(@PathVariable("username") String username) {
        Usuario usuario = uS.findByUsername(username);
        ModelMapper m = new ModelMapper();
        UsuarioDTO dto = m.map(usuario, UsuarioDTO.class);
        return ResponseEntity.ok(dto);
    }
}
