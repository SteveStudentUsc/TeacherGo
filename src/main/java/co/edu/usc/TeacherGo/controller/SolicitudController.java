package co.edu.usc.TeacherGo.controller;

import co.edu.usc.TeacherGo.model.Docentes;
import co.edu.usc.TeacherGo.model.Solicitudes;
import co.edu.usc.TeacherGo.model.Usuarios;
import co.edu.usc.TeacherGo.repository.DocenteRepository;
import co.edu.usc.TeacherGo.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    // Método para crear una solicitud (esto lo maneja el estudiante)
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

        // Buscar el docente por ID
        Docentes docente = docenteRepository.findById(docenteId).orElse(null);
        if (docente == null) {
            return "error"; // Puedes personalizar esta vista de error si lo deseas
        }

        // Crear y guardar la solicitud
        Solicitudes solicitud = new Solicitudes();
        solicitud.setEstudiante(estudiante);
        solicitud.setDocente(docente);
        solicitud.setFechaHora(fechaHora);
        solicitud.setEstado("Pendiente");

        solicitudRepository.save(solicitud);

        // Redirigir al perfil del docente con el mensaje de éxito
        return "redirect:/docentes/" + docenteId + "?solicitudExitosa";
    }

    // Método para ver las solicitudes pendientes del docente logueado
    @GetMapping("/docente")
    public String verSolicitudesDocente(HttpSession session, Model model) {
        // Obtener usuario logueado desde la sesión
        Usuarios docenteLogueado = (Usuarios) session.getAttribute("usuarioLogueado");
        if (docenteLogueado == null) {
            // Si no está logueado, redirigir a login
            return "redirect:/login";
        }

        // Obtener las solicitudes pendientes del docente
        List<Solicitudes> solicitudes = solicitudRepository.findByDocente(docenteLogueado);

        // Si no hay solicitudes pendientes, mostrar mensaje
        if (solicitudes.isEmpty()) {
            model.addAttribute("mensaje", "No tienes solicitudes pendientes.");
        } else {
            model.addAttribute("solicitudes", solicitudes);
        }

        return "solicitudes"; // Vista que muestra las solicitudes del docente
    }

    @PostMapping("/actualizar")
    public String actualizarEstadoSolicitud(
            @RequestParam Long solicitudId,
            @RequestParam String estado,
            HttpSession session) {

        Usuarios docenteLogueado = (Usuarios) session.getAttribute("usuarioLogueado");

        Solicitudes solicitud = solicitudRepository.findById(solicitudId).orElse(null);
        if (solicitud == null || !solicitud.getDocente().getId().equals(docenteLogueado.getId())) {
            return "error"; // O una vista diferente
        }

        solicitud.setEstado(estado);
        solicitudRepository.save(solicitud);

        return "redirect:/solicitudes/docente";
    }

}
