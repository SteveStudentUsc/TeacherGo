package co.edu.usc.TeacherGo.controller;

import co.edu.usc.TeacherGo.model.Usuarios;
import co.edu.usc.TeacherGo.model.Estado;
import co.edu.usc.TeacherGo.repository.UsuarioRepository;
import co.edu.usc.TeacherGo.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ControllerTeacherGo {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    // Registro de usuarios
    @GetMapping("/registro")
  //  @ResponseBody me aseguro que posman me haga get del body
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "registro";
    }

    @PostMapping("/registrar")
   //@ResponseBody
    public String registrarUsuario(@ModelAttribute("usuario") Usuarios usuario, Model model) {
        try {
            usuarioRepository.save(usuario);
            return "redirect:/registro-exitoso";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/registro-exitoso")
   // @ResponseBody
    public String mostrarRegistroExitoso() {
        return "registro-exitoso";
    }

    // Perfil
    @GetMapping("/perfil/editar")
    public String mostrarFormularioEditarPerfil(Model model, @SessionAttribute("usuarioLogueado") Usuarios usuarioActual) {
        model.addAttribute("usuario", usuarioActual);

        // Agregar historial de estados al modelo
        List<Estado> estados = estadoRepository.findByUsuario(usuarioActual);
        model.addAttribute("estados", estados);

        return "gestion-perfil";
    }

    @PostMapping("/perfil/editar")
    public String actualizarPerfil(
            @ModelAttribute("usuario") Usuarios usuarioForm,
            @SessionAttribute("usuarioLogueado") Usuarios usuarioActual,
            @RequestParam("accion") String accion,
            @RequestParam(value = "estado", required = false) String nuevoEstado,
            Model model) {

        try {
            switch (accion) {
                case "correo":
                    usuarioActual.setCorreo(usuarioForm.getCorreo());
                    break;
                case "contrasena":
                    usuarioActual.setContrasena(usuarioForm.getContrasena());
                    break;
                case "estado":
                    if (nuevoEstado != null && !nuevoEstado.trim().isEmpty()) {
                        Estado estado = new Estado();
                        estado.setUsuario(usuarioActual);
                        estado.setEstado(nuevoEstado);
                        estado.setFecha(LocalDateTime.now());
                        estadoRepository.save(estado);
                    } else {
                        model.addAttribute("error", "El estado no puede estar vacío.");
                        return mostrarFormularioEditarPerfil(model, usuarioActual);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Acción no válida: " + accion);
            }

            usuarioRepository.save(usuarioActual);
            return "redirect:/perfil/editar";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar: " + e.getMessage());
            return mostrarFormularioEditarPerfil(model, usuarioActual);
        }
    }

    // Editar un estado existente
    @PostMapping("/perfil/editarEstado")
    public String editarEstado(
            @RequestParam("estadoId") Long estadoId,
            @RequestParam("nuevoEstado") String nuevoTexto,
            @SessionAttribute("usuarioLogueado") Usuarios usuarioActual,
            Model model) {
        Optional<Estado> estadoOptional = estadoRepository.findById(estadoId);

        if (estadoOptional.isPresent()) {
            Estado estado = estadoOptional.get();

            // Seguridad: validar que el estado pertenezca al usuario
            if (!estado.getUsuario().getId().equals(usuarioActual.getId())) {
                model.addAttribute("error", "No tienes permisos para editar este estado.");
                return "redirect:/perfil/editar";
            }

            estado.setEstado(nuevoTexto);
            estado.setFecha(LocalDateTime.now());
            estadoRepository.save(estado);
        }

        return "redirect:/perfil/editar";
    }

    // Eliminar un estado existente
    @PostMapping("/perfil/eliminarEstado")
    public String eliminarEstado(
            @RequestParam("estadoId") Long estadoId,
            @SessionAttribute("usuarioLogueado") Usuarios usuarioActual,
            Model model) {
        Optional<Estado> estadoOptional = estadoRepository.findById(estadoId);

        if (estadoOptional.isPresent()) {
            Estado estado = estadoOptional.get();

            // Seguridad: validar que el estado pertenezca al usuario
            if (!estado.getUsuario().getId().equals(usuarioActual.getId())) {
                model.addAttribute("error", "No tienes permisos para eliminar este estado.");
                return "redirect:/perfil/editar";
            }

            estadoRepository.deleteById(estadoId);
        }

        return "redirect:/perfil/editar";
    }
}
