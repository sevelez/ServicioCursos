package com.proyectofs.edutechInnovators.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class SeccionDocenteId implements Serializable {

    @Column(name = "ID_SECCION")
    private Long seccionId;

    @Column(name = "ID_DOCENTE")
    private Long docenteId;
}
