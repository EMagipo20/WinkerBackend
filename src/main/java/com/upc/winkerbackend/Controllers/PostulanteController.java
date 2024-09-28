package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.PostulanteDTO;
import com.upc.winkerbackend.Entities.Postulante;
import com.upc.winkerbackend.ServiceImplements.PostulanteServiceImplements;
import com.upc.winkerbackend.ServiceImplements.UsuarioServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Postulantes")
@PreAuthorize("hasAnyAuthority('POSTULANTE')")
public class PostulanteController {

    @Autowired
    private UsuarioServiceImplements uS;

    @Autowired
    private PostulanteServiceImplements pS;
    @Autowired

    private ModelMapper modelMapper;

    @PostMapping("/AgregarPostulante")
    public ResponseEntity<String> agregar(@RequestBody PostulanteDTO postulanteDTO) {
        pS.insertarPostulante(postulanteDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/Actualizar/{id}")
    public ResponseEntity<String> actualizar(@RequestBody PostulanteDTO postulanteDTO) {
        Postulante postulante = modelMapper.map(postulanteDTO, Postulante.class);
        pS.actualizarPostulante(postulante);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public List<Postulante> listarPostulantes() {
        return pS.listarTodo();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        pS.eliminarPostulante(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/Obtener/{id}")
    public ResponseEntity<PostulanteDTO> obtenerPorId(@PathVariable("id") Long id) {
        PostulanteDTO postulanteDTO = pS.obtenerPostulantePorId(id);
        return ResponseEntity.ok(postulanteDTO);
    }

    @GetMapping("/BuscarPorNombre/{nombre}")
    public ResponseEntity<List<PostulanteDTO>> buscarPorNombre(@PathVariable("nombre") String nombreCompleto) {
        List<PostulanteDTO> postulantes = pS.buscarPostulantePorNombre(nombreCompleto);
        return ResponseEntity.ok(postulantes);
    }

    @GetMapping("/BuscarPorCorreo/{correo}")
    public ResponseEntity<List<PostulanteDTO>> buscarPorCorreo(@PathVariable("correo") String correo) {
        List<PostulanteDTO> postulantes = pS.buscarPostulantePorCorreo(correo);
        return ResponseEntity.ok(postulantes);
    }

    @GetMapping("/SolicitudesAceptadas")
    public ResponseEntity<List<PostulanteDTO>> postulantesConSolicitudesAceptadas() {
        List<PostulanteDTO> postulantes = pS.postulantesConSolicitudesAceptadas();
        return ResponseEntity.ok(postulantes);
    }

    @GetMapping("/obtenerId/{username}")
    public ResponseEntity<Long> PostulanteIdByUsername(@PathVariable String username) {
        try {
            Long postulanteId = pS.findPostulanteIdByUsername(username);
            return ResponseEntity.ok(postulanteId);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count-posts")
    public ResponseEntity<List<Object[]>> getCountPostsPorPostulante() {
        List<Object[]> result = pS.countPostsPorPostulante();
        return ResponseEntity.ok(result);
    }
}
