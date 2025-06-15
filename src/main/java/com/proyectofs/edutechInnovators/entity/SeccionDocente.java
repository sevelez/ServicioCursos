package com.proyectofs.edutechInnovators.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "SECCIONES_DOCENTE", schema = "BDEDUTECH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeccionDocente {

    @EmbeddedId
    private SeccionDocenteId id;

    @ManyToOne
    @MapsId("seccionId")
    @JoinColumn(name = "ID_SECCION")
    private Seccion seccion;

    @ManyToOne
    @MapsId("docenteId")
    @JoinColumn(name = "ID_DOCENTE")
    private Docente docente;

    @Column(name = "FECHA_CONTRATO")
    private LocalDate fechaContrato;
}