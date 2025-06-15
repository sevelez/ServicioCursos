package com.proyectofs.edutechInnovators.controller;

import com.proyectofs.edutechInnovators.entity.Seccion;
import com.proyectofs.edutechInnovators.entity.Curso;
import com.proyectofs.edutechInnovators.services.SeccionService;
import com.proyectofs.edutechInnovators.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class ServCursController {

    private final SeccionService seccionService;
    private final CursoService cursoService;

    @Autowired
    public ServCursController(SeccionService seccionService, CursoService cursoService) {
        this.seccionService = seccionService;
        this.cursoService = cursoService;
    }

    // Seccion Controller
    @GetMapping("/secciones")
    public ResponseEntity<List<Seccion>> getAllSecciones() {
        return ResponseEntity.ok(seccionService.getAllSecciones());
    }

    @GetMapping("/secciones/{id}")
    public ResponseEntity<Seccion> getSeccionById(@PathVariable Long id) {
        Optional<Seccion> seccion = seccionService.getSeccionById(id);
        return seccion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/secciones")
    public ResponseEntity<Seccion> createSeccion(@RequestBody Seccion seccion) {
        try {
            Seccion nuevaSeccion = seccionService.createSeccion(seccion);
            return new ResponseEntity<>(nuevaSeccion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/secciones/{id}")
    public ResponseEntity<Seccion> updateSeccion(@PathVariable Long id, @RequestBody Seccion seccion) {
        Optional<Seccion> actualizada = seccionService.updateSeccion(id, seccion);
        return actualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/secciones/{id}")
    public ResponseEntity<Void> deleteSeccion(@PathVariable Long id) {
        if (seccionService.deleteSeccion(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/secciones/{seccionId}/docente/{docenteId}")
    public ResponseEntity<Map<String, Object>> asignarDocenteASeccion(
            @PathVariable Long seccionId,
            @PathVariable Long docenteId,
            @RequestBody Map<String, String> body) {

        String fechaContrato = body.get("fechaContrato");
        // Valida que seccionId y docenteId existan, y que la fecha sea válida.
        try {
            seccionService.asignarDocenteASeccion(seccionId, docenteId, fechaContrato);
            Map<String, Object> response = new HashMap<>();
            response.put("seccionId", seccionId);
            response.put("docenteId", docenteId);
            response.put("fechaContrato", fechaContrato);
            response.put("message", "Docente asignado a sección correctamente.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "No se pudo asignar el docente a la seccion");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/secciones/{seccionId}/docente/{docenteId}")
    public ResponseEntity<?> moverDocente(
            @PathVariable Long seccionId,
            @PathVariable Long docenteId,
            @RequestBody Map<String, Object> body) {

        try {
            // Obtener nueva sección destino y fecha del JSON
            Long seccionDestino = Long.valueOf(body.get("seccionDestino").toString());
            String fechaContratoStr = body.get("fechaContrato").toString();
            LocalDate fechaContrato = LocalDate.parse(fechaContratoStr);

            // Elimina asignación actual y asigna a una nueva sección.
            seccionService.eliminarAsignacion(seccionId, docenteId);
            seccionService.asignarDocenteASeccion(seccionDestino, docenteId, fechaContrato.toString());

            return ResponseEntity.ok(Map.of(
                    "message", "Docente movido correctamente",
                    "Seccion de Origen", seccionId,
                    "Seccion de Destino", seccionDestino,
                    "docenteId", docenteId,
                    "fechaContrato", fechaContrato
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "No se pudo mover al docente",
                    "message", e.getMessage()
            ));
        }

    }

    @DeleteMapping("/secciones/{seccionId}/docente/{docenteId}")
    public ResponseEntity<?> eliminarAsignacionDocente(
            @PathVariable Long seccionId,
            @PathVariable Long docenteId) {
        try {
            seccionService.eliminarAsignacion(seccionId, docenteId);
            return ResponseEntity.ok(Map.of("message", "Asignación eliminada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "No se pudo eliminar la asignación",
                    "message", e.getMessage()
            ));
        }
    }

    // Curso Controller
    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> getAllCursos() {
        return ResponseEntity.ok(cursoService.getAllCursos());
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.getCursoById(id);
        return curso.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/cursos")
    public ResponseEntity<Curso> createCurso(@RequestBody Curso curso) {
        Curso nuevo = cursoService.createCurso(curso);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PutMapping("/cursos/{id}")
    public ResponseEntity<Curso> updateCurso(@PathVariable Long id, @RequestBody Curso curso) {
        Optional<Curso> actualizado = cursoService.updateCurso(id, curso);
        return actualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        if (cursoService.deleteCurso(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}