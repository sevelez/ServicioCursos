package com.proyectofs.edutechInnovators.services;

import com.proyectofs.edutechInnovators.entity.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> getAllCursos();
    Optional<Curso> getCursoById(Long id);
    Curso createCurso(Curso curso);
    Optional<Curso> updateCurso(Long id, Curso curso);
    boolean deleteCurso(Long id);
}
