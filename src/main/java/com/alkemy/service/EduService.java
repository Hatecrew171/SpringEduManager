package com.alkemy.service;

import com.alkemy.entity.*;
import com.alkemy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EduService {
    @Autowired private EstudianteRepository estudianteRepo;
    @Autowired private CursoRepository cursoRepo;
    @Autowired private InscripcionRepository inscripcionRepo;
    @Autowired private EvaluacionRepository evaluacionRepo;

    public List<Curso> obtenerTodosCursos() { return cursoRepo.findAll(); }
    public List<Estudiante> obtenerTodosEstudiantes() { return estudianteRepo.findAll(); }
    public List<Inscripcion> obtenerTodasInscripciones() { return inscripcionRepo.findAll(); }
    
    public Estudiante guardarEstudiante(Estudiante e) { return estudianteRepo.save(e); }
    public Curso guardarCurso(Curso c) { return cursoRepo.save(c); }

    @Transactional
    public void solicitarInscripcion(String email, Long cursoId) {
        Estudiante est = estudianteRepo.findByEmail(email).orElseThrow();
        Curso cur = cursoRepo.findById(cursoId).orElseThrow();
        Inscripcion ins = new Inscripcion();
        ins.setEstudiante(est);
        ins.setCurso(cur);
        ins.setEstado("PENDIENTE");
        inscripcionRepo.save(ins);
    }

    @Transactional
    public void gestionarSolicitud(Long id, String estado, String motivo) {
        Inscripcion ins = inscripcionRepo.findById(id).orElseThrow();
        ins.setEstado(estado);
        ins.setMotivo(motivo);
        inscripcionRepo.save(ins);
    }

    public void registrarNota(Long estId, Long cursoId, String test, Integer nota) {
        Evaluacion ev = new Evaluacion();
        ev.setEstudiante(estudianteRepo.findById(estId).get());
        ev.setCurso(cursoRepo.findById(cursoId).get());
        ev.setNombreTest(test);
        ev.setNota(nota);
        evaluacionRepo.save(ev);
    }
    
    public List<Evaluacion> obtenerNotasEstudiante(String email) {
        return evaluacionRepo.findAll().stream()
            .filter(ev -> ev.getEstudiante().getEmail().equals(email))
            .toList();
    }
    
    public List<Inscripcion> obtenerInscripcionesPorEmail(String email) {
        return inscripcionRepo.findAll().stream()
                .filter(i -> i.getEstudiante().getEmail().equals(email))
                .toList();
    }

    public Inscripcion obtenerInscripcionPorId(Long id) {
        return inscripcionRepo.findById(id).orElseThrow();
    }
    
 // --- MÉTODOS DE ELIMINACIÓN ---
    public void eliminarCurso(Long id) { cursoRepo.deleteById(id); }
    public void eliminarEstudiante(Long id) { estudianteRepo.deleteById(id); }
    public void eliminarNota(Long id) { evaluacionRepo.deleteById(id); }

    // --- MÉTODOS DE BÚSQUEDA PARA EDICIÓN ---
    public Curso obtenerCursoPorId(Long id) { return cursoRepo.findById(id).orElseThrow(); }
    public Estudiante obtenerEstudiantePorId(Long id) { return estudianteRepo.findById(id).orElseThrow(); }
    public List<Evaluacion> obtenerTodasEvaluaciones() { return evaluacionRepo.findAll(); }
}
