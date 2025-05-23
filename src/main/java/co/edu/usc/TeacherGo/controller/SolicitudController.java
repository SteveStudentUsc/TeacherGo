package co.edu.usc.TeacherGo.controller;

import co.edu.usc.TeacherGo.model.Docentes;
import co.edu.usc.TeacherGo.model.Solicitudes;
import co.edu.usc.TeacherGo.model.Usuarios;
import co.edu.usc.TeacherGo.repository.DocenteRepository;
import co.edu.usc.TeacherGo.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @PostMapping("/crear")
    public String crearSolicitud(
            @RequestParam Long docenteId,
            @RequestParam String fechaHora,
            HttpSession session) {

        // Obtener usuario logueado desde la sesión
        Usuarios estudiante = (Usuarios) session.getAttribute("usuarioLogueado");
        if (estudiante == null) {
            // Redirigir a login o mostrar error si no hay usuario en sesión
            return "redirect:/login"; // O página que uses para login
        }

        Docentes docente = docenteRepository.findById(docenteId).orElse(null);
        if (docente == null) {
            return "error"; // Puedes personalizar esta vista
        }

        Solicitudes solicitud = new Solicitudes();
        solicitud.setEstudiante(estudiante);
        solicitud.setDocente(docente);
        solicitud.setFechaHora(fechaHora);
        solicitud.setEstado("Pendiente");

        solicitudRepository.save(solicitud);

        return "redirect:/docentes/" + docenteId + "?solicitudExitosa";
    }
}
