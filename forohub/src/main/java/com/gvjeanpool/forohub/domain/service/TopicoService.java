package com.gvjeanpool.forohub.domain.service;

import com.gvjeanpool.forohub.domain.dto.TopicoRequestDTO;
import com.gvjeanpool.forohub.domain.dto.TopicoResponseDTO;
import com.gvjeanpool.forohub.domain.exception.CourseNotFoundException;
import com.gvjeanpool.forohub.domain.exception.DuplicateTopicException;
import com.gvjeanpool.forohub.domain.exception.TopicNotFoundException;
import com.gvjeanpool.forohub.domain.model.Curso;
import com.gvjeanpool.forohub.domain.model.Topico;
import com.gvjeanpool.forohub.domain.model.TopicoStatus;
import com.gvjeanpool.forohub.domain.model.Usuario;
import com.gvjeanpool.forohub.domain.repository.CursoRepository;
import com.gvjeanpool.forohub.domain.repository.TopicoRepository;
import com.gvjeanpool.forohub.domain.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public TopicoService(TopicoRepository topicoRepository,
            CursoRepository cursoRepository,
            UsuarioRepository usuarioRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public TopicoResponseDTO crearTopico(TopicoRequestDTO request, Long usuarioId) {
        // Verificar duplicado por titulo + mensaje (regla de negocio del challenge)
        if (topicoRepository.existsByTituloAndMensaje(request.getTitulo(), request.getMensaje())) {
            throw new DuplicateTopicException(request.getTitulo(), request.getMensaje());
        }

        // Get course
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new CourseNotFoundException(request.getCursoId()));

        // Get user
        Usuario autor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setFechaCreacion(LocalDateTime.now());
        topico.setStatus(TopicoStatus.NO_RESPONDIDO);
        topico.setAutor(autor);
        topico.setCurso(curso);

        Topico saved = topicoRepository.save(topico);
        return new TopicoResponseDTO(saved);
    }

    public Page<TopicoResponseDTO> listarTopicos(Pageable pageable, String cursoNombre) {
        Page<Topico> topicos;
        if (cursoNombre != null && !cursoNombre.isEmpty()) {
            topicos = topicoRepository.findByCursoNombre(cursoNombre, pageable);
        } else {
            topicos = topicoRepository.findAll(pageable);
        }
        return topicos.map(TopicoResponseDTO::new);
    }

    public TopicoResponseDTO obtenerTopico(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
        return new TopicoResponseDTO(topico);
    }

    @Transactional
    public TopicoResponseDTO actualizarTopico(Long id, TopicoRequestDTO request) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));

        // Verificar duplicado por titulo + mensaje (excluyendo el tópico actual)
        Topico duplicate = topicoRepository.findByTituloAndMensaje(request.getTitulo(), request.getMensaje())
                .orElse(null);

        if (duplicate != null && !duplicate.getId().equals(id)) {
            throw new DuplicateTopicException(request.getTitulo(), request.getMensaje());
        }

        // Update course if provided
        if (request.getCursoId() != null) {
            Curso curso = cursoRepository.findById(request.getCursoId())
                    .orElseThrow(() -> new CourseNotFoundException(request.getCursoId()));
            topico.setCurso(curso);
        }

        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());

        Topico updated = topicoRepository.save(topico);
        return new TopicoResponseDTO(updated);
    }

    @Transactional
    public void eliminarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new TopicNotFoundException(id);
        }
        topicoRepository.deleteById(id);
    }
}
