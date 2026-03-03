package com.gvjeanpool.forohub.infrastructure.controller;

import com.gvjeanpool.forohub.domain.dto.TopicoRequestDTO;
import com.gvjeanpool.forohub.domain.dto.TopicoResponseDTO;
import com.gvjeanpool.forohub.domain.service.TopicoService;
import com.gvjeanpool.forohub.infrastructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;
    private final JwtService jwtService;

    public TopicoController(TopicoService topicoService, JwtService jwtService) {
        this.topicoService = topicoService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> crearTopico(@Valid @RequestBody TopicoRequestDTO request,
            HttpServletRequest httpRequest) {
        Long usuarioId = getCurrentUserId(httpRequest);
        TopicoResponseDTO response = topicoService.crearTopico(request, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String curso) {
        Page<TopicoResponseDTO> topicos = topicoService.listarTopicos(pageable, curso);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> obtenerTopico(@PathVariable Long id) {
        TopicoResponseDTO response = topicoService.obtenerTopico(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> actualizarTopico(@PathVariable Long id,
            @Valid @RequestBody TopicoRequestDTO request) {
        TopicoResponseDTO response = topicoService.actualizarTopico(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = jwtService.getUserIdFromToken(token);
            if (userId != null) {
                return userId;
            }
        }
        throw new RuntimeException("No se pudo obtener el usuario autenticado");
    }
}
