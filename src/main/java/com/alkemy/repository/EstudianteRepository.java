package com.alkemy.repository;

import com.alkemy.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    // Spring entiende esto automáticamente, no necesitas escribir el SQL
    Optional<Estudiante> findByEmail(String email);
    Optional<Estudiante> findByNombre(String nombre);
}