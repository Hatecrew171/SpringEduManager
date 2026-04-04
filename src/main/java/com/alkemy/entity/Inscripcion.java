package com.alkemy.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Inscripcion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne private Estudiante estudiante;
    @ManyToOne private Curso curso;
    private String estado = "PENDIENTE";
    private String motivo;

    // Genera o escribe los Getters y Setters para todos los campos aquí
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}