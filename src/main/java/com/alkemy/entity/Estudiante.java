package com.alkemy.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String email;

    @ManyToMany
    @JoinTable(
      name = "inscripciones", 
      joinColumns = @JoinColumn(name = "estudiante_id"), 
      inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private Set<Curso> cursosInscritos = new HashSet<>();

    // Constructor vacío (Obligatorio para JPA)
    public Estudiante() {}

    // Getters y Setters Manuales
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<Curso> getCursosInscritos() { return cursosInscritos; }
    public void setCursosInscritos(Set<Curso> cursosInscritos) { this.cursosInscritos = cursosInscritos; }
}