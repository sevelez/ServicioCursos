package com.proyectofs.edutechInnovators.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DOCENTE", schema = "BDEDUTECH")
@Getter
@Setter

public class Docente {

    @Id
    @Column(name = "ID_DOCENTE")
    private Long id;

    @Column(name = "NOMBRE_DOCENTE")
    private String nombreDocente;

    public Docente() {}

    public Docente(Long id, String nombreDocente) {
        this.id = id;
        this.nombreDocente = nombreDocente;
    }
}
