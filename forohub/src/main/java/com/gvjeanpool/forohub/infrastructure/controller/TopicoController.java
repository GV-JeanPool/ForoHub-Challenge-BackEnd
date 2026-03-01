package com.gvjeanpool.forohub.infrastructure.controller;

import com.gvjeanpool.forohub.domain.dto.TopicoRequestDTO;
import com.gvjeanpool.forohub.domain.dto.TopicoResponseDTO;
import com.gvjeanpool.forohub.domain.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> crearTopico(@Valid @RequestBody TopicoRequestDTO request) {
        // Get current user ID (for demo, using user ID 1)
        Long usuarioId = getCurrentUserId();
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

    private Long getCurrentUserId() {
        // For demo purposes, return user ID 1
        // In production, get from JWT token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return 1L;
        }
        return 1L;
    }
}
