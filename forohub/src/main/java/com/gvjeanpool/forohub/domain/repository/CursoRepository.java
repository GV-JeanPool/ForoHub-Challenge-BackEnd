package com.gvjeanpool.forohub.domain.repository;

import com.gvjeanpool.forohub.domain.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
