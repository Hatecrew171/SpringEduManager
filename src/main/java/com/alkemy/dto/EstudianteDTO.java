package com.alkemy.dto;
import java.util.List;

public record EstudianteDTO(String nombre, String email, List<NotaDTO> notas) {}