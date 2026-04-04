package com.alkemy.controller;

import com.alkemy.entity.*;
import com.alkemy.service.EduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class WebController {

    @Autowired 
    private EduService eduService;

    // --- VISTA PRINCIPAL (SOLO UNA VEZ) ---
    @GetMapping("/cursos")
    public String listarCursos(Model model, Principal principal) {
        model.addAttribute("cursos", eduService.obtenerTodosCursos());
        
        if (principal != null && !principal.getName().equals("admin")) {
            // Datos para el Estudiante
            model.addAttribute("solicitudesUsuario", eduService.obtenerInscripcionesPorEmail(principal.getName()));
            model.addAttribute("misNotas", eduService.obtenerNotasEstudiante(principal.getName()));
        } else {
            // Datos vacíos para el Admin para evitar errores en el HTML
            model.addAttribute("solicitudesUsuario", new ArrayList<>());
        }
        return "cursos-lista";
    }

    // --- RUTAS DE REGISTRO ---
    @GetMapping("/nuevo-curso")
    public String formularioCurso(Model model) {
        model.addAttribute("curso", new Curso());
        return "curso-form";
    }

    @PostMapping("/guardar-curso")
    public String guardarCurso(@ModelAttribute Curso curso) {
        eduService.guardarCurso(curso);
        return "redirect:/cursos";
    }

    @GetMapping("/nuevo-estudiante")
    public String formularioEstudiante(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "estudiante-form";
    }

    @PostMapping("/guardar-estudiante")
    public String guardarEstudiante(@ModelAttribute Estudiante estudiante) {
        eduService.guardarEstudiante(estudiante);
        return "redirect:/cursos";
    }

    // --- RUTAS DE INSCRIPCIÓN ---
    @GetMapping("/inscribir/{id}")
    public String solicitar(@PathVariable Long id, Principal principal) {
        eduService.solicitarInscripcion(principal.getName(), id);
        return "redirect:/cursos?solicitado=true";
    }

    // --- RUTAS DE ADMINISTRACIÓN ---
    @GetMapping("/admin/solicitudes")
    public String verSolicitudes(Model model) {
        model.addAttribute("solicitudes", eduService.obtenerTodasInscripciones());
        return "solicitudes-admin";
    }

    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", eduService.obtenerTodosEstudiantes());
        return "admin-usuarios";
    }

    @PostMapping("/admin/gestionar")
    public String gestionar(@RequestParam Long id, @RequestParam String estado, @RequestParam String motivo) {
        eduService.gestionarSolicitud(id, estado, motivo);
        return "redirect:/admin/solicitudes";
    }

    @GetMapping("/admin/calificar/{inscripcionId}")
    public String mostrarFormCalificar(@PathVariable Long inscripcionId, Model model) {
        Inscripcion ins = eduService.obtenerInscripcionPorId(inscripcionId);
        model.addAttribute("inscripcion", ins);
        return "admin-calificar";
    }

    @PostMapping("/admin/guardar-nota")
    public String guardarNota(@RequestParam Long estudianteId, 
                             @RequestParam Long cursoId, 
                             @RequestParam String nombreTest, 
                             @RequestParam Integer nota) {
        eduService.registrarNota(estudianteId, cursoId, nombreTest, nota);
        return "redirect:/cursos"; // O a donde prefieras redirigir
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
 // --- EDICIÓN Y BORRADO DE CURSOS ---
    @GetMapping("/admin/cursos/editar/{id}")
    public String editarCursoForm(@PathVariable Long id, Model model) {
        model.addAttribute("curso", eduService.obtenerCursoPorId(id));
        return "curso-form"; // Reutilizamos el mismo formulario
    }

    @GetMapping("/admin/cursos/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id) {
        eduService.eliminarCurso(id);
        return "redirect:/cursos";
    }

    // --- EDICIÓN Y BORRADO DE ESTUDIANTES ---
    @GetMapping("/admin/estudiantes/editar/{id}")
    public String editarEstudianteForm(@PathVariable Long id, Model model) {
        model.addAttribute("estudiante", eduService.obtenerEstudiantePorId(id));
        return "estudiante-form";
    }

    @GetMapping("/admin/estudiantes/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id) {
        eduService.eliminarEstudiante(id);
        return "redirect:/admin/usuarios";
    }

    // --- VISTA DE TODAS LAS NOTAS (PARA EL ADMIN) ---
    @GetMapping("/admin/notas")
    public String listarTodasLasNotas(Model model) {
        model.addAttribute("notas", eduService.obtenerTodasEvaluaciones());
        return "admin-notas";
    }

    @GetMapping("/admin/notas/eliminar/{id}")
    public String eliminarNota(@PathVariable Long id) {
        eduService.eliminarNota(id);
        return "redirect:/admin/notas";
    }
}