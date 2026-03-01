package com.gvjeanpool.forohub.domain.repository;

import com.gvjeanpool.forohub.domain.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByTopicoId(Long topicoId);
    List<Respuesta> findByTopicoIdAndSolucionTrue(Long topicoId);
}
