package com.proyectofs.edutechInnovators.services;

import com.proyectofs.edutechInnovators.entity.*;
import com.proyectofs.edutechInnovators.repository.CursoRepository;
import com.proyectofs.edutechInnovators.repository.DocenteRepository;
import com.proyectofs.edutechInnovators.repository.SeccionRepository;
import com.proyectofs.edutechInnovators.repository.SeccionDocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository seccionRepository;
    private final CursoRepository cursoRepository;
    private final DocenteRepository docenteRepository;
    private final SeccionDocenteRepository seccionDocenteRepository;

    @Autowired
    public SeccionServiceImpl(SeccionRepository seccionRepository,
                              CursoRepository cursoRepository,
                              DocenteRepository docenteRepository,
                              SeccionDocenteRepository seccionDocenteRepository) {
        this.seccionRepository = seccionRepository;
        this.cursoRepository = cursoRepository;
        this.docenteRepository = docenteRepository;
        this.seccionDocenteRepository = seccionDocenteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seccion> getAllSecciones() {
        return seccionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seccion> getSeccionById(Long id) {
        return seccionRepository.findById(id);
    }

    @Override
    @Transactional
    public Seccion createSeccion(Seccion seccion) {
        Long cursoId = seccion.getCurso().getId();
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + cursoId));
        seccion.setCurso(curso);
        return seccionRepository.save(seccion);
    }

    @Override
    @Transactional
    public Optional<Seccion> updateSeccion(Long id, Seccion seccion) {
        return seccionRepository.findById(id).map(existing -> {
            Long cursoId = seccion.getCurso().getId();
            Curso curso = cursoRepository.findById(cursoId)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + cursoId));
            seccion.setId(id);
            seccion.setCurso(curso);
            return seccionRepository.save(seccion);
        });
    }

    @Override
    @Transactional
    public boolean deleteSeccion(Long id) {
        if (seccionRepository.existsById(id)) {
            seccionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void asignarDocenteASeccion(Long seccionId, Long docenteId, String fechaContrato) throws Exception {
        Seccion seccion = seccionRepository.findById(seccionId)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada con ID: " + seccionId));
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + docenteId));

        LocalDate fechaContratoDate;

        try {
            fechaContratoDate = LocalDate.parse(fechaContrato, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
        }

        if (seccionDocenteRepository.existsBySeccionIdAndDocenteId(seccionId, docenteId)) {
            throw new RuntimeException("El docente ya está asignado a esta sección.");
        }

        SeccionDocenteId id = new SeccionDocenteId(seccionId, docenteId);
        SeccionDocente seccionDocente = new SeccionDocente();
        seccionDocente.setId(id);
        seccionDocente.setSeccion(seccion);
        seccionDocente.setDocente(docente);
        seccionDocente.setFechaContrato(LocalDate.parse(fechaContratoDate.toString()));

        seccionDocenteRepository.save(seccionDocente);
    }

    @Override
    @Transactional
    public void eliminarAsignacion(Long seccionId, Long docenteId) {
        SeccionDocenteId id = new SeccionDocenteId(seccionId, docenteId);
        if (!seccionDocenteRepository.existsById(id)) {
            throw new RuntimeException("No existe asignación para eliminar.");
        }
        seccionDocenteRepository.deleteById(id);
    }
}
