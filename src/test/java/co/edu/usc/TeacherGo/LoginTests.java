package co.edu.usc.TeacherGo;

import co.edu.usc.TeacherGo.model.Usuarios;
import co.edu.usc.TeacherGo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import co.edu.usc.TeacherGo.model.TipoUsuario;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Se ejecuta antes de cada prueba para garantizar que exista un usuario válido
    @BeforeEach
    void prepararUsuarioDePrueba() {
        String correo = "usuario@valido.com";
        String contrasena = "contrasena123";

        if (!usuarioRepository.findByCorreoAndContrasena(correo, contrasena).isPresent()) {
            Usuarios nuevoUsuario = new Usuarios();
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setNombre("Usuario Prueba");
            nuevoUsuario.setTipo(TipoUsuario.valueOf("Estudiante"));
            usuarioRepository.save(nuevoUsuario);
        }
    }

    // Test que carga correctamente la vista de login
    @Test
    void testMostrarFormularioLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    // Test para login exitoso (usuario válido redirige al panel)
    @Test
    void testLoginExitoso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("correo", "carlos.lopez@example.com")
                        .param("contrasena", "securepass"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/panel-usuario"));
    }

    // Test para login fallido (credenciales incorrectas)
    @Test
    void testLoginFallido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("correo", "esteusuarioexiste@correo.com")
                        .param("contrasena", "parecequeno123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"));
    }
}
