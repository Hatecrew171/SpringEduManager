package com.alkemy.repository;

import com.alkemy.entity.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}