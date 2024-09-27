package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.EmpresaDTO;
import com.upc.winkerbackend.Entities.Empresa;
import com.upc.winkerbackend.Entities.Rubro;
import com.upc.winkerbackend.Entities.Usuario;
import com.upc.winkerbackend.Repositories.EmpresaRepository;
import com.upc.winkerbackend.Repositories.RubroRepository;
import com.upc.winkerbackend.Repositories.UsuarioRepository;
import com.upc.winkerbackend.ServiceInterface.EmpresaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImplements implements EmpresaService {
    @Autowired
    private EmpresaRepository eR;

    @Autowired
    private UsuarioRepository uR;

    @Autowired
    private RubroRepository rR;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${app.max-photo-size}")
    private int maxPhotoSize;

    // IMAGEN
    private static final byte[] PNG_HEADER = {(byte) 0x89, 0x50, 0x4E, 0x47}; // .PNG
    private static final byte[] JPG_HEADER = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}; // .JPG

    @Override
    public Empresa insertarEmpresa(EmpresaDTO empresaDTO) {
        Empresa empresa = new Empresa();
        empresa.setNombreEmpresa(empresaDTO.getNombre());
        empresa.setRuc(empresaDTO.getRuc());
        empresa.setTelefono(empresaDTO.getTelefono());

        if (empresaDTO.getLogotipoBase64() != null) {
            byte[] photoBytes = Base64.getDecoder().decode(empresaDTO.getLogotipoBase64());
            if (photoBytes.length > maxPhotoSize) {
                throw new IllegalArgumentException("El tamaño del logotipo excede el límite máximo permitido de 2MB");
            }
            if (!isValidFormat(photoBytes, PNG_HEADER, JPG_HEADER)) {
                throw new IllegalArgumentException("Formato de logotipo inválido. Solo se aceptan PNG y JPG");
            }
            empresa.setLogotipo(photoBytes);
        }
        // Agregar el usuario asociado
        Usuario usuario = uR.findById(empresaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        empresa.setUsuario(usuario);
        // Agregar el rubro asociado
        Rubro rubro = rR.findById(empresaDTO.getRubroId())
                .orElseThrow(() -> new RuntimeException("Rubro no encontrado"));
        empresa.setRubro(rubro);
        return eR.save(empresa);
    }

    private boolean isValidFormat(byte[] fileBytes, byte[]... headers) {
        for (byte[] header : headers) {
            if (fileBytes.length >= header.length && Arrays.equals(Arrays.copyOf(fileBytes, header.length), header)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actualizarEmpresa(Empresa empresa) {
        // Recupera la empresa existente desde la base de datos
        Empresa empresaExistente = eR.findById(empresa.getId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Copia todos los campos excepto el logotipo de la entidad actualizada
        empresaExistente.setNombreEmpresa(empresa.getNombreEmpresa());
        empresaExistente.setRuc(empresa.getRuc());
        empresaExistente.setTelefono(empresa.getTelefono());
        empresaExistente.setRubro(empresa.getRubro());
        // No se actualiza el logotipo

        // Guarda la entidad actualizada sin modificar el logotipo
        eR.save(empresaExistente);
    }

    @Override
    public void eliminarEmpresa(Long id) {
        if (!eR.existsById(id)) {
            throw new RuntimeException("La empresa con ID " + id + " no fue encontrada.");
        }
        eR.deleteById(id);
        logger.info("Empresa con ID {} eliminada exitosamente", id);
    }


    @Override
    public List<EmpresaDTO> listarEmpresas() {
        return eR.findAll().stream()
                .map(empresa -> modelMapper.map(empresa, EmpresaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmpresaDTO obtenerEmpresaPorId(Long id) {
        Empresa empresa = eR.findById(id).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        return modelMapper.map(empresa, EmpresaDTO.class);
    }

    @Override
    public List<EmpresaDTO> buscarEmpresaPorRuc(String ruc) {
        return eR.findByRuc(ruc)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmpresaDTO> buscarEmpresaPorNombre(String nombreEmpresa) {
        return eR.findByNombreEmpresa(nombreEmpresa)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long contarTotalEmpresas() {
        return eR.countTotalEmpresas();
    }

    @Override
    public String findNombreEmpresaByUsername(String username) {
        return eR.findNombreEmpresaByUsuarioUsername(username);
    }

    private EmpresaDTO convertToDTO(Empresa empresa) {
        return modelMapper.map(empresa, EmpresaDTO.class);
    }
}
