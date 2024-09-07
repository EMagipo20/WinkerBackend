package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.DTO.PostulanteDTO;
import com.upc.winkerbackend.Entities.Postulante;
import com.upc.winkerbackend.Entities.Usuario;
import com.upc.winkerbackend.Repositories.PostulanteRepository;
import com.upc.winkerbackend.Repositories.UsuarioRepository;
import com.upc.winkerbackend.ServiceInterface.PostulanteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulanteServiceImplements implements PostulanteService {
    @Autowired
    private PostulanteRepository pR;

    @Autowired
    private UsuarioRepository uR;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${app.max-photo-size}")
    private int maxPhotoSize;

    // IMAGEN
    private static final byte[] PNG_HEADER = {(byte) 0x89, 0x50, 0x4E, 0x47}; // .PNG
    private static final byte[] JPG_HEADER = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}; // .JPG

    @Override
    public Postulante insertarPostulante(PostulanteDTO postulanteDTO) {
        Postulante postulante = new Postulante();
        postulante.setNombreCompleto(postulanteDTO.getNombreCompleto());
        postulante.setCorreo(postulanteDTO.getCorreo());
        postulante.setTelefono(postulanteDTO.getTelefono());
        postulante.setFechaNacimiento(postulanteDTO.getFechaNacimiento());
        postulante.setDireccion(postulanteDTO.getDireccion());

        if (postulanteDTO.getFotoBase64() != null) {
            byte[] photoBytes = Base64.getDecoder().decode(postulanteDTO.getFotoBase64());
            if (photoBytes.length > maxPhotoSize) {
                throw new IllegalArgumentException("El tamaño de la foto excede el límite máximo permitido de 2MB");
            }
            if (!isValidFormat(photoBytes, PNG_HEADER, JPG_HEADER)) {
                throw new IllegalArgumentException("Formato de foto inválido. Solo se aceptan PNG y JPG");
            }
            postulante.setFoto(photoBytes);
        }
        // Agregar el usuario asociado
        Usuario usuario = uR.findById(postulanteDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        postulante.setUsuario(usuario);
        return pR.save(postulante);
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
    public Postulante actualizarPostulante(Postulante postulante) {
        // Recupera el postulante existente desde la base de datos
        Postulante postulanteExistente = pR.findById(postulante.getId())
                .orElseThrow(() -> new RuntimeException("Postulante no encontrado con ID: " + postulante.getId()));

        // Copia todos los campos excepto la foto de la entidad actualizada
        postulanteExistente.setNombreCompleto(postulante.getNombreCompleto());
        postulanteExistente.setCorreo(postulante.getCorreo());
        postulanteExistente.setTelefono(postulante.getTelefono());
        postulanteExistente.setFechaNacimiento(postulante.getFechaNacimiento());
        postulanteExistente.setDireccion(postulante.getDireccion());
        // No se actualiza la foto

        // Guarda la entidad actualizada sin modificar la foto
        return pR.save(postulanteExistente);
    }

    @Override
    public List<Postulante> listarTodo() {
        return pR.findAll();
    }

    @Override
    public void eliminarPostulante(Long id) {
        pR.deleteById(id);
    }

    @Override
    public PostulanteDTO obtenerPostulantePorId(Long id) {
        Postulante postulante = pR.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulante no encontrado con ID: " + id));
        return convertToDTO(postulante);
    }

    @Override
    public List<PostulanteDTO> buscarPostulantePorNombre(String nombreCompleto) {
        return pR.findByNombreCompletoContaining(nombreCompleto)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostulanteDTO> buscarPostulantePorCorreo(String correo) {
        return pR.findByCorreoContaining(correo)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostulanteDTO> postulantesConSolicitudesAceptadas() {
        return pR.findPostulantesConSolicitudesAceptadas()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long findPostulanteIdByUsername(String username) {
        return pR.findPostulanteIdByUsername(username).orElseThrow(() -> new RuntimeException("Postulante no encontrado"));
    }

    @Override
    public List<Object[]> countPostsPorPostulante() {
        return pR.countPostsPorPostulante();
    }

    private PostulanteDTO convertToDTO(Postulante postulante) {
        return modelMapper.map(postulante, PostulanteDTO.class);
    }
}
