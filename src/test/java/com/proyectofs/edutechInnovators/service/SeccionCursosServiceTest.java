package com.proyectofs.edutechInnovators.service;

import com.proyectofs.edutechInnovators.entity.Curso;
import com.proyectofs.edutechInnovators.entity.Seccion;
import com.proyectofs.edutechInnovators.repository.CursoRepository;
import com.proyectofs.edutechInnovators.repository.SeccionRepository;
import com.proyectofs.edutechInnovators.services.CursoServiceImpl;

import com.proyectofs.edutechInnovators.services.SeccionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeccionCursosServiceTest {
    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private SeccionRepository seccionRepository;

    @InjectMocks
    private SeccionServiceImpl seccionService;

    @Test
    void guardarCursoySeccion() {
        Curso curso = new Curso("Base de datos", "curso de base de datos");
        curso.setId(1L);

        Seccion seccion = new Seccion("Taller de Base de datos", curso);
        seccion.setId(10L);

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso)); // <--- Este es clave
        when(seccionRepository.save(any(Seccion.class))).thenReturn(seccion);

        Seccion resultadoSeccion = seccionService.createSeccion(seccion);

        assertNotNull(resultadoSeccion);
        assertEquals("Taller de Base de datos", resultadoSeccion.getNombreSeccion());
        verify(seccionRepository, times(1)).save(seccion);
    }

    @Test
    void buscarSeccionPorId() {

        Curso curso = new Curso("Base de datos", "curso de base de datos");
        curso.setId(1L);

        Seccion seccion = new Seccion("Taller de Base de datos", curso);
        seccion.setId(10L);

        when(seccionRepository.findById(10L)).thenReturn(Optional.of(seccion));

        Optional<Seccion> resultado = seccionService.getSeccionById(10L);

        assertTrue(resultado.isPresent());
        assertEquals("Taller de Base de datos", resultado.get().getNombreSeccion());
        verify(seccionRepository, times(1)).findById(10L);
    }

    @Test
    void eliminarSeccionPorId_existente() {
        Long id = 10L;
        when(seccionRepository.existsById(id)).thenReturn(true);

        boolean resultado = seccionService.deleteSeccion(id);

        assertTrue(resultado);
        verify(seccionRepository, times(1)).existsById(id);
        verify(seccionRepository, times(1)).deleteById(id);
    }

    @Test
    void actualizarSeccionCurso() {
        // Arrange
        Long seccionId = 10L;
        Long cursoId = 1L;

        Curso cursoExistente = new Curso("Base de datos", "Curso de base de BD");
        cursoExistente.setId(cursoId);

        Seccion seccionExistente = new Seccion("BDY123", cursoExistente);
        seccionExistente.setId(seccionId);

        Curso nuevoCurso = new Curso("Redes", "Curso nuevo");
        nuevoCurso.setId(cursoId);

        Seccion seccionModificada = new Seccion("Sección actualizada", nuevoCurso);

        when(seccionRepository.findById(seccionId)).thenReturn(Optional.of(seccionExistente));
        when(cursoRepository.findById(cursoId)).thenReturn(Optional.of(nuevoCurso));
        when(seccionRepository.save(any(Seccion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Seccion> resultado = seccionService.updateSeccion(seccionId, seccionModificada);

        assertTrue(resultado.isPresent());
        assertEquals("Sección actualizada", resultado.get().getNombreSeccion());
        assertEquals("Redes", resultado.get().getCurso().getNombre());

        verify(seccionRepository, times(1)).findById(seccionId);
        verify(cursoRepository, times(1)).findById(cursoId);
        verify(seccionRepository, times(1)).save(any(Seccion.class));
    }

}
