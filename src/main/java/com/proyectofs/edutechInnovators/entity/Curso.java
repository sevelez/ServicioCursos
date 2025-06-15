package com.proyectofs.edutechInnovators.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CURSOS", schema = "BDEDUTECH")
@Getter
@Setter
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curso_seq_gen")
    @SequenceGenerator(name = "curso_seq_gen", sequenceName = "SEQ_CURSOS", allocationSize = 1)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;
}

