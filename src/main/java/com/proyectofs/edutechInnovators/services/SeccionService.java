package com.proyectofs.edutechInnovators.services;

import com.proyectofs.edutechInnovators.entity.Seccion;
import java.util.List;
import java.util.Optional;

public interface SeccionService {
    List<Seccion> getAllSecciones();
    Optional<Seccion> getSeccionById(Long id);
    Seccion createSeccion(Seccion seccion);
    Optional<Seccion> updateSeccion(Long id, Seccion seccion);
    boolean deleteSeccion(Long id);
    void asignarDocenteASeccion(Long seccionId, Long docenteId, String fechaContrato) throws Exception;
    void eliminarAsignacion(Long seccionId, Long docenteId);
}