package com.proyectofs.edutechInnovators.repository;

import com.proyectofs.edutechInnovators.entity.SeccionDocente;
import com.proyectofs.edutechInnovators.entity.SeccionDocenteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeccionDocenteRepository extends JpaRepository<SeccionDocente, SeccionDocenteId> {
    boolean existsBySeccionIdAndDocenteId(Long seccionId, Long docenteId);
}


