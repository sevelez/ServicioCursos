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

   /* @Test
    @Transactional
    @Rollback
    @Order(1)
    void guardarCurso() {
        Curso curso = new Curso(1L, "Base de datos", "3er nivel de base de datos");
        //Seccion seccion = new Seccion(1L,"Taller de Base de datos", curso);
        Curso cursoGuardado = cursoRepository.save(curso);
        //Seccion seccionGuardada = seccionRepository.save(seccion);

        assertNotNull(cursoGuardado.getId());
        assertEquals("Base de datos", curso.getNombre());
        //assertNotNull(seccionGuardada.getId());
        //assertEquals("Taller de Base de Datos", seccion.getNombreSeccion());

    }*/
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

}
