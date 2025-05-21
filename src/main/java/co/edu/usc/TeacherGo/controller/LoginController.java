package co.edu.usc.TeacherGo.controller;

import co.edu.usc.TeacherGo.model.Login;
import co.edu.usc.TeacherGo.model.Docentes;
import co.edu.usc.TeacherGo.model.Usuarios;
import co.edu.usc.TeacherGo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Muestra el formulario de login
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    // Procesa el formulario de login
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Login login,
                                Model model,
                                HttpSession session) {
        Usuarios usuario = usuarioRepository
                .findByCorreoAndContrasena(login.getCorreo(), login.getContrasena())
                .orElse(null);

        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/panel-usuario";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }
    }

    // Muestra la página de bienvenida
    @GetMapping("/panel-usuario")
    public String vistaBienvenida(HttpSession session, Model model) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "panel-usuario";
    }

    // Cierra la sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
