package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.EmpresaDTO;
import com.upc.winkerbackend.DTO.PostulanteDTO;
import com.upc.winkerbackend.Entities.Empresa;
import com.upc.winkerbackend.ServiceImplements.EmpresaServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Empresas")
@PreAuthorize("hasAnyAuthority('EMPRESA', 'POSTULANTE')")
public class EmpresaController {
    @Autowired
    private EmpresaServiceImplements eS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody EmpresaDTO empresaDTO) {
        eS.insertarEmpresa(empresaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = modelMapper.map(empresaDTO, Empresa.class);
        eS.actualizarEmpresa(empresa);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        eS.eliminarEmpresa(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<EmpresaDTO>> listarTodo() {
        List<EmpresaDTO> empresas = eS.listarEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/Obtener/{id}")
    public ResponseEntity<EmpresaDTO> obtenerPorId(@PathVariable("id") Long id) {
        EmpresaDTO empresaDTO = eS.obtenerEmpresaPorId(id);
        return ResponseEntity.ok(empresaDTO);
    }

    @GetMapping("/BuscarPorRuc/{ruc}")
    public ResponseEntity<List<EmpresaDTO>> buscarPorRuc(@PathVariable("ruc") String ruc) {
        List<EmpresaDTO> empresas = eS.buscarEmpresaPorRuc(ruc);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/BuscarPorNombre/{nombre}")
    public ResponseEntity<List<EmpresaDTO>> buscarPorNombre(@PathVariable("nombre") String nombreEmpresa) {
        List<EmpresaDTO> empresas = eS.buscarEmpresaPorNombre(nombreEmpresa);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/ContarTotal")
    public ResponseEntity<Long> contarTotal() {
        long count = eS.contarTotalEmpresas();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/nombrePorUsername/{username}")
    public ResponseEntity<String> NombreEmpresaByUsername(@PathVariable String username) {
        String nombreEmpresa = eS.findNombreEmpresaByUsername(username);
        return ResponseEntity.ok(nombreEmpresa);
    }
}
