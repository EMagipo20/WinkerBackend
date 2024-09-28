package com.upc.winkerbackend.Controllers;

import com.upc.winkerbackend.DTO.EmpresaDTO;
import com.upc.winkerbackend.Entities.Empresa;
import com.upc.winkerbackend.ServiceImplements.EmpresaServiceImplements;
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
@RequestMapping("/Empresas")
@PreAuthorize("hasAnyAuthority('EMPRESA', 'POSTULANTE')")
public class EmpresaController {
    @Autowired
    private EmpresaServiceImplements eS;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/Agregar")
    public ResponseEntity<String> agregar(@RequestBody EmpresaDTO empresaDTO) {
        try {
            eS.insertarEmpresa(empresaDTO);
            return ResponseEntity.ok("Empresa agregada con éxito.");
        } catch (DataException ex) {
            throw new DataException("Error al agregar empresa: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al agregar empresa.");
        }
    }

    @PutMapping("/Actualizar")
    public ResponseEntity<String> actualizar(@RequestBody EmpresaDTO empresaDTO) {
        try {
            Empresa empresa = modelMapper.map(empresaDTO, Empresa.class);
            eS.actualizarEmpresa(empresa);
            return ResponseEntity.ok("Empresa actualizada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Empresa no encontrada para actualizar: " + ex.getMessage());
        } catch (DataException ex) {
            throw new DataException("Error al actualizar empresa: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al actualizar empresa.");
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        try {
            eS.eliminarEmpresa(id);
            return ResponseEntity.ok("Empresa eliminada con éxito.");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Empresa no encontrada para eliminar: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al eliminar empresa.");
        }
    }

    @GetMapping("/ListarTodo")
    public ResponseEntity<List<EmpresaDTO>> listarTodo() {
        try {
            List<EmpresaDTO> empresas = eS.listarEmpresas();
            return ResponseEntity.ok(empresas);
        } catch (Exception ex) {
            throw new ErrorException("Error al listar empresas.");
        }
    }

    @GetMapping("/Obtener/{id}")
    public ResponseEntity<EmpresaDTO> obtenerPorId(@PathVariable("id") Long id) {
        try {
            EmpresaDTO empresaDTO = eS.obtenerEmpresaPorId(id);
            return ResponseEntity.ok(empresaDTO);
        } catch (NotFoundException ex) {
            throw new NotFoundException("Empresa no encontrada con ID: " + ex.getMessage());
        } catch (Exception ex) {
            throw new ErrorException("Error al obtener empresa por ID.");
        }
    }

    @GetMapping("/BuscarPorRuc/{ruc}")
    public ResponseEntity<List<EmpresaDTO>> buscarPorRuc(@PathVariable("ruc") String ruc) {
        try {
            List<EmpresaDTO> empresas = eS.buscarEmpresaPorRuc(ruc);
            return ResponseEntity.ok(empresas);
        } catch (Exception ex) {
            throw new ErrorException("Error al buscar empresas por RUC.");
        }
    }

    @GetMapping("/BuscarPorNombre/{nombre}")
    public ResponseEntity<List<EmpresaDTO>> buscarPorNombre(@PathVariable("nombre") String nombreEmpresa) {
        try {
            List<EmpresaDTO> empresas = eS.buscarEmpresaPorNombre(nombreEmpresa);
            return ResponseEntity.ok(empresas);
        } catch (Exception ex) {
            throw new ErrorException("Error al buscar empresas por nombre.");
        }
    }

    @GetMapping("/ContarTotal")
    public ResponseEntity<Long> contarTotal() {
        try {
            long count = eS.contarTotalEmpresas();
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            throw new ErrorException("Error al contar total de empresas.");
        }
    }

    @GetMapping("/nombrePorUsername/{username}")
    public ResponseEntity<String> NombreEmpresaByUsername(@PathVariable String username) {
        try {
            String nombreEmpresa = eS.findNombreEmpresaByUsername(username);
            return ResponseEntity.ok(nombreEmpresa);
        } catch (Exception ex) {
            throw new ErrorException("Error al obtener el nombre de la empresa por nombre de usuario.");
        }
    }
}
