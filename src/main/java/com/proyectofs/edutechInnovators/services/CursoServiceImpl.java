package com.proyectofs.edutechInnovators.services;

import com.proyectofs.edutechInnovators.entity.Curso;
import com.proyectofs.edutechInnovators.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> getCursoById(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public Optional<Curso> updateCurso(Long id, Curso curso) {
        return cursoRepository.findById(id).map(existing -> {
            existing.setNombre(curso.getNombre());
            existing.setDescripcion(curso.getDescripcion());
            return cursoRepository.save(existing);
        });
    }

    @Override
    @Transactional
    public boolean deleteCurso(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}