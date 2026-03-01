package com.gvjeanpool.forohub.infrastructure.controller;

import com.gvjeanpool.forohub.domain.dto.AuthenticationDTO;
import com.gvjeanpool.forohub.domain.dto.TokenDTO;
import com.gvjeanpool.forohub.domain.dto.UsuarioRequestDTO;
import com.gvjeanpool.forohub.domain.dto.UsuarioResponseDTO;
import com.gvjeanpool.forohub.domain.service.AuthenticationService;
import com.gvjeanpool.forohub.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationService authenticationService, UsuarioService usuarioService) {
        this.authenticationService = authenticationService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody AuthenticationDTO request) {
        TokenDTO token = authenticationService.authenticate(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO response = usuarioService.crearUsuario(request);
        return ResponseEntity.ok(response);
    }
}
