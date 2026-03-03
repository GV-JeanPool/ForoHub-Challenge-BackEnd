package com.gvjeanpool.forohub.domain.service;

import com.gvjeanpool.forohub.domain.dto.UsuarioRequestDTO;
import com.gvjeanpool.forohub.domain.dto.UsuarioResponseDTO;
import com.gvjeanpool.forohub.domain.model.Usuario;
import com.gvjeanpool.forohub.domain.repository.PerfilRepository;
import com.gvjeanpool.forohub.domain.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
            PerfilRepository perfilRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByCorreoElectronico(request.getCorreoElectronico())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreoElectronico(request.getCorreoElectronico());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        // Asignar solo el rol por defecto ROLE_USER (nunca ROLE_ADMIN al registrarse)
        perfilRepository.findByNombre("ROLE_USER")
                .ifPresent(perfil -> usuario.getPerfiles().add(perfil));

        Usuario saved = usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(saved);
    }

    public UsuarioResponseDTO obtenerUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UsuarioResponseDTO(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreoElectronico(correo)
                .orElse(null);
    }
}
