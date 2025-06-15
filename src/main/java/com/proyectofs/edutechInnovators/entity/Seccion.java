package com.proyectofs.edutechInnovators.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SECCIONES", schema = "BDEDUTECH")
@Getter
@Setter
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seccion_seq_gen")
    @SequenceGenerator(name = "seccion_seq_gen", sequenceName = "SEQ_SECCIONES", allocationSize = 1)
    @Column(name = "ID_SECCION")
    private Long id;

    @Column(name = "NOMBRE_SECCION")
    private String nombreSeccion;

    @ManyToOne
    @JoinColumn(name = "ID_CURSO")
    private Curso curso;

}