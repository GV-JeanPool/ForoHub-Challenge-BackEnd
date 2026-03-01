package com.gvjeanpool.forohub.domain.service;

import com.gvjeanpool.forohub.domain.dto.AuthenticationDTO;
import com.gvjeanpool.forohub.domain.dto.TokenDTO;
import com.gvjeanpool.forohub.domain.model.Usuario;
import com.gvjeanpool.forohub.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                               JwtService jwtService,
                               UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    public TokenDTO authenticate(AuthenticationDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreoElectronico(),
                        request.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        Usuario usuario = usuarioService.buscarPorCorreo(request.getCorreoElectronico());
        String token = jwtService.generateToken(usuario);
        
        return new TokenDTO(token);
    }
}
