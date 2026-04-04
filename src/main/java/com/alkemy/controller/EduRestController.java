package com.alkemy.controller;

import com.alkemy.dto.*;
import com.alkemy.entity.*;
import com.alkemy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class EduRestController {

    @Autowired private CursoRepository cursoRepo;
    @Autowired private EstudianteRepository estudianteRepo;
    @Autowired private InscripcionRepository inscripcionRepo;
    @Autowired private EvaluacionRepository evaluacionRepo;

    @GetMapping("/cursos")
    public List<CursoDTO> getCursos() {
        List<Inscripcion> todasInscripciones = inscripcionRepo.findAll();
        
        return cursoRepo.findAll().stream().map(curso -> {
            List<String> aprobados = todasInscripciones.stream()
                .filter(ins -> ins.getCurso().getId().equals(curso.getId()) && ins.getEstado().equals("APROBADO"))
                .map(ins -> ins.getEstudiante().getNombre())
                .collect(Collectors.toList());
            return new CursoDTO(curso.getId(), curso.getTitulo(), aprobados);
        }).collect(Collectors.toList());
    }

    @GetMapping("/estudiantes")
    public List<EstudianteDTO> getEstudiantes() {
        List<Evaluacion> todasNotas = evaluacionRepo.findAll();
        
        return estudianteRepo.findAll().stream().map(est -> {
            List<NotaDTO> notasEst = todasNotas.stream()
                .filter(n -> n.getEstudiante().getId().equals(est.getId()))
                .map(n -> new NotaDTO(n.getCurso().getTitulo(), n.getNombreTest(), n.getNota()))
                .collect(Collectors.toList());
            return new EstudianteDTO(est.getNombre(), est.getEmail(), notasEst);
        }).collect(Collectors.toList());
    }

    @GetMapping("/admin/pendientes")
    public List<Inscripcion> getPendientes() {
        return inscripcionRepo.findAll().stream()
                .filter(i -> i.getEstado().equals("PENDIENTE"))
                .collect(Collectors.toList());
    }
    
    // 1. Agregar Estudiante por API
    @PostMapping("/estudiantes")
    public EstudianteDTO crearEstudiante(@RequestBody Estudiante nuevoEstudiante) {
        Estudiante guardado = estudianteRepo.save(nuevoEstudiante);
        // Usamos ArrayList vacío porque un estudiante nuevo no tiene notas aún
        return new EstudianteDTO(guardado.getNombre(), guardado.getEmail(), new ArrayList<>());
    }

    // 2. Aprobar o Rechazar una solicitud por API
    // Se envía el ID de la inscripción y el nuevo estado (APROBADO/RECHAZADO)
    @PostMapping("/inscripciones/gestionar")
    public String gestionarInscripcion(@RequestParam Long id, @RequestParam String estado, @RequestParam String motivo) {
        Inscripcion ins = inscripcionRepo.findById(id).orElseThrow();
        ins.setEstado(estado);
        ins.setMotivo(motivo);
        inscripcionRepo.save(ins);
        return "Estado actualizado a: " + estado;
    }

    // 3. Asignar Notas por API
    @PostMapping("/notas")
    public NotaDTO asignarNota(@RequestParam Long estudianteId, 
                               @RequestParam Long cursoId, 
                               @RequestParam String nombreTest, 
                               @RequestParam Integer nota) {
        
        Estudiante est = estudianteRepo.findById(estudianteId).orElseThrow();
        Curso cur = cursoRepo.findById(cursoId).orElseThrow();
        
        Evaluacion ev = new Evaluacion();
        ev.setEstudiante(est);
        ev.setCurso(cur);
        ev.setNombreTest(nombreTest);
        ev.setNota(nota);
        evaluacionRepo.save(ev);
        
        return new NotaDTO(cur.getTitulo(), nombreTest, nota);
    }
}