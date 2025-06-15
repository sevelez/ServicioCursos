package com.proyectofs.edutechInnovators.repository;

import com.proyectofs.edutechInnovators.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
}
