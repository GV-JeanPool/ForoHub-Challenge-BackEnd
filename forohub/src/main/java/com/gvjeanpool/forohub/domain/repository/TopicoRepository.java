package com.gvjeanpool.forohub.domain.repository;

import com.gvjeanpool.forohub.domain.model.Topico;
import com.gvjeanpool.forohub.domain.model.TopicoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    
    boolean existsByTitulo(String titulo);
    
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
    
    Optional<Topico> findByTitulo(String titulo);
    
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso")
    Page<Topico> findByCursoNombre(@Param("nombreCurso") String nombreCurso, Pageable pageable);
    
    Page<Topico> findByStatus(TopicoStatus status, Pageable pageable);
}
