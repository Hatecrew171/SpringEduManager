package com.alkemy.entity;

import jakarta.persistence.*;

@Entity
public class Evaluacion {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombreTest;
    private Integer nota;

    @ManyToOne 
    private Estudiante estudiante;

    @ManyToOne 
    private Curso curso;

    // --- GETTERS Y SETTERS MANUALES (Para eliminar los errores de EduService) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTest() {
        return nombreTest;
    }

    public void setNombreTest(String nombreTest) {
        this.nombreTest = nombreTest;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}