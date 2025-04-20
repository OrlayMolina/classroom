package co.edu.uniquendio.aulas.virtuales.service;

import co.edu.uniquendio.aulas.virtuales.config.JWTUtils;
import co.edu.uniquendio.aulas.virtuales.dto.LoginDTO;
import co.edu.uniquendio.aulas.virtuales.dto.TokenDTO;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import co.edu.uniquendio.aulas.virtuales.dto.UsuarioDTO;
import co.edu.uniquendio.aulas.virtuales.exception.AuthenticationException;
import co.edu.uniquendio.aulas.virtuales.exception.ResourceNotFoundException;
import co.edu.uniquendio.aulas.virtuales.model.Usuario;
import co.edu.uniquendio.aulas.virtuales.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.edu.uniquendio.aulas.virtuales.config.CryptoPassword.encriptarPassword;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @PersistenceContext
    private EntityManager entityManager;

    private final JWTUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo usuario utilizando el procedimiento almacenado PK_USUARIO.PCREAR_USUARIO
     */
    @Transactional
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_USUARIO.PCREAR_USUARIO")
                .registerStoredProcedureParameter("email", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("clave", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("documento_id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("apellido", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("rol_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("usuario_id", Long.class, ParameterMode.OUT);

        query.setParameter("email", usuarioDTO.getEmail());
        String claveEncriptada = encriptarPassword(usuarioDTO.getClave());
        query.setParameter("clave", claveEncriptada);
        query.setParameter("documento_id", usuarioDTO.getDocumentoId());
        query.setParameter("nombre", usuarioDTO.getNombre());
        query.setParameter("apellido", usuarioDTO.getApellido());
        query.setParameter("rol_id", usuarioDTO.getRolId());

        query.execute();

        Long usuarioId = (Long) query.getOutputParameterValue("usuario_id");
        usuarioDTO.setUsuarioId(usuarioId);

        return usuarioDTO;
    }

    /**
     * Actualiza un usuario existente utilizando el procedimiento almacenado PK_USUARIO.PACTUALIZAR_USUARIO
     */
    @Transactional
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        // Verificamos que el usuario existe
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_USUARIO.PACTUALIZAR_USUARIO")
                .registerStoredProcedureParameter("usuario_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("email", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("clave", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("documento_id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("apellido", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("rol_id", Long.class, ParameterMode.IN);

        String claveEncriptada;
        if (usuarioDTO.getClave() != null && !usuarioDTO.getClave().isEmpty()) {
            claveEncriptada = encriptarPassword(usuarioDTO.getClave());
        } else {
            Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow();
            claveEncriptada = usuarioExistente.getClave();
        }
        query.setParameter("usuario_id", id);
        query.setParameter("email", usuarioDTO.getEmail());
        query.setParameter("clave", claveEncriptada);
        query.setParameter("documento_id", usuarioDTO.getDocumentoId());
        query.setParameter("nombre", usuarioDTO.getNombre());
        query.setParameter("apellido", usuarioDTO.getApellido());
        query.setParameter("rol_id", usuarioDTO.getRolId());

        query.execute();

        usuarioDTO.setUsuarioId(id);
        return usuarioDTO;
    }

    /**
     * Elimina un usuario utilizando el procedimiento almacenado PK_USUARIO.PELIMINAR_USUARIO
     */
    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_USUARIO.PELIMINAR_USUARIO")
                .registerStoredProcedureParameter("usuario_id", Long.class, ParameterMode.IN);

        query.setParameter("usuario_id", id);
        query.execute();
    }

    /**
     * Autentica un usuario utilizando la función PK_USUARIO.PAUTENTICAR_USUARIO
     */
    /**
     * Autentica un usuario utilizando la función PK_USUARIO.PVALIDAR_CREDENCIALES
     */
    @Transactional(readOnly = true)
    public TokenDTO autenticarUsuario(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginDTO.getEmail());

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuario no encontrado en la base de datos");
            throw new AuthenticationException("Credenciales inválidas");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getClave())) {
            System.out.println("La contraseña no coincide");
            throw new AuthenticationException("Credenciales inválidas");
        }

        Map<String, Object> map = construirClaims(usuario);
        try {
            String token = jwtUtils.generarToken(usuario.getEmail(), map);
            return new TokenDTO(token);
        } catch (Exception e) {
            System.err.println("Error en la generación del token: " + e.getClass().getName());
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private Map<String, Object> construirClaims(Usuario usuario) {
        return Map.of(
                "user_id", usuario.getUsuarioId(),
                "email", usuario.getEmail(),
                "rol_id", usuario.getRol()
        );
    }

    /**
     * Verifica si un usuario es profesor utilizando la función PK_USUARIO.PES_PROFESOR
     */
    @Transactional(readOnly = true)
    public boolean esProfesor(Long usuarioId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_USUARIO.PES_PROFESOR")
                .registerStoredProcedureParameter("p_usuario_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("RETURN_VALUE", Boolean.class, ParameterMode.OUT);

        query.setParameter("p_usuario_id", usuarioId);
        query.execute();

        return (Boolean) query.getOutputParameterValue("RETURN_VALUE");
    }

    /**
     * Verifica si un usuario es estudiante utilizando la función PK_USUARIO.PES_ESTUDIANTE
     */
    @Transactional(readOnly = true)
    public boolean esEstudiante(Long usuarioId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_USUARIO.PES_ESTUDIANTE")
                .registerStoredProcedureParameter("p_usuario_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("RETURN_VALUE", Boolean.class, ParameterMode.OUT);

        query.setParameter("p_usuario_id", usuarioId);
        query.execute();

        return (Boolean) query.getOutputParameterValue("RETURN_VALUE");
    }

    /**
     * Obtiene la lista de estudiantes de un curso utilizando la función PK_USUARIO.POBTENER_ESTUDIANTES_CURSO
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerEstudiantesCurso(Long cursoId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_USUARIO.POBTENER_ESTUDIANTES_CURSO")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", cursoId);
        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        List<UsuarioDTO> estudiantes = new ArrayList<>();
        for (Object[] row : resultList) {
            UsuarioDTO estudiante = new UsuarioDTO();
            estudiante.setUsuarioId((Long) row[0]);
            estudiante.setEmail((String) row[1]);
            estudiante.setClave(""); // No devolvemos la clave por seguridad
            estudiante.setDocumentoId((String) row[2]);
            estudiante.setNombre((String) row[3]);
            estudiante.setApellido((String) row[4]);
            estudiante.setRolId((Long) row[5]);

            estudiantes.add(estudiante);
        }

        return estudiantes;
    }

    /**
     * Obtiene un usuario por su ID
     */
    @Transactional(readOnly = true)
    public UsuarioDTO getUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        usuarioDTO.setClave(""); // No devolvemos la clave por seguridad

        return usuarioDTO;
    }

    /**
     * Obtiene todos los usuarios (sin devolver las claves)
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> {
                    UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
                    dto.setClave(""); // No devolvemos la clave por seguridad
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los profesores
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarProfesores() {
        List<Usuario> profesores = usuarioRepository.findByRolRolId(1L); // Asumiendo que ROL_ID = 1 es para profesores
        return profesores.stream()
                .map(profesor -> {
                    UsuarioDTO dto = modelMapper.map(profesor, UsuarioDTO.class);
                    dto.setClave(""); // No devolvemos la clave por seguridad
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los estudiantes
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarEstudiantes() {
        List<Usuario> estudiantes = usuarioRepository.findByRolRolId(2L); // Asumiendo que ROL_ID = 2 es para estudiantes
        return estudiantes.stream()
                .map(estudiante -> {
                    UsuarioDTO dto = modelMapper.map(estudiante, UsuarioDTO.class);
                    dto.setClave(""); // No devolvemos la clave por seguridad
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
