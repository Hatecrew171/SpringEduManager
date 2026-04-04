package com.alkemy.dto;
import java.util.List;

public record CursoDTO(Long id, String titulo, List<String> alumnosAprobados) {}