package com.proyectofs.edutechInnovators.repository;

import com.proyectofs.edutechInnovators.entity.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {
}
