package com.proyectofs.edutechInnovators.repository;
import com.proyectofs.edutechInnovators.entity.Curso;
import com.proyectofs.edutechInnovators.entity.Seccion;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeccionCursosIntegrationTest {
    @Autowired
    private SeccionRepository seccionRepository;
    @Autowired
    private CursoRepository cursoRepository;

   @Test
   @Transactional
   @Rollback
   @Order(1)
   void guardarCursoYSeccion() {
       Curso curso = new Curso("Base de datos", "3er nivel de base de datos");
       Curso cursoGuardado = cursoRepository.save(curso);

       Seccion seccion = new Seccion("Taller de Base de datos", cursoGuardado);
       Seccion seccionGuardada = seccionRepository.save(seccion);

       assertNotNull(cursoGuardado.getId());
       assertNotNull(seccionGuardada.getId());
       assertEquals("Taller de Base de datos", seccionGuardada.getNombreSeccion());
   }

   @Test
   @Order(2)
    void buscarSeccionPorId() {
       Curso curso = new Curso("Base de datos", "3er nivel de base de datos");
       Curso cursoGuardado = cursoRepository.save(curso);

       Seccion seccion = new Seccion("Taller de Base de datos", cursoGuardado);
       Seccion seccionGuardada = seccionRepository.save(seccion);

       Optional<Seccion> seccionEncontrada = seccionRepository.findById(seccionGuardada.getId());

       assertTrue(seccionEncontrada.isPresent());
       assertEquals("Taller de Base de datos", seccionEncontrada.get().getNombreSeccion());
   }

    @Test
    @Transactional
    @Rollback
    @Order(3)
    void eliminarCursoYSeccion() {
        Curso curso = new Curso("Base de datos", "3er nivel de base de datos");
        Curso cursoGuardado = cursoRepository.save(curso);

        Seccion seccion = new Seccion("Taller de Base de datos", cursoGuardado);
        Seccion seccionGuardada = seccionRepository.save(seccion);

        assertNotNull(cursoGuardado.getId());
        assertNotNull(seccionGuardada.getId());
        assertEquals("Taller de Base de datos", seccionGuardada.getNombreSeccion());


        seccionRepository.deleteById(seccionGuardada.getId());
        cursoRepository.deleteById(cursoGuardado.getId());

        assertFalse(seccionRepository.findById(seccionGuardada.getId()).isPresent());
        assertFalse(cursoRepository.findById(cursoGuardado.getId()).isPresent());

    }

    @Test
    @Transactional
    @Rollback
    @Order(4)
    void actualizarCursoYSeccion() {
        Curso curso = new Curso("Base de datos", "3er nivel de base de datos");
        Curso cursoGuardado = cursoRepository.save(curso);

        Seccion seccion = new Seccion("Taller de Base de datos", cursoGuardado);
        Seccion seccionGuardada = seccionRepository.save(seccion);

        assertNotNull(cursoGuardado.getId());
        assertNotNull(seccionGuardada.getId());
        assertEquals("Taller de Base de datos", seccionGuardada.getNombreSeccion());

        cursoGuardado.setNombre("Base de datos actualizada");
        cursoGuardado.setDescripcion("Descripción actualizada");

        seccionGuardada.setNombreSeccion("Sección actualizada");

        Curso cursoActualizado = cursoRepository.save(cursoGuardado);
        Seccion seccionActualizada = seccionRepository.save(seccionGuardada);

        assertEquals("Base de datos actualizada", cursoActualizado.getNombre());
        assertEquals("Descripción actualizada", cursoActualizado.getDescripcion());
        assertEquals("Sección actualizada", seccionActualizada.getNombreSeccion());
    }

}
