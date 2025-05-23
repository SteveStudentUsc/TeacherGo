package co.edu.usc.TeacherGo;


import co.edu.usc.TeacherGo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
class TeacherGoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired

	private UsuarioRepository usuarioRepository;

	@Test
	void contextLoads() {
		// verificamos la carga
	}


	// Test carga vista de  el formulario de registro GET
	@Test
	void testMostrarFormularioRegistro() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/registro"))  // Petición GET a "/registro"
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("registro"));  // Verifica que la vista sea "registro"
	}


	// Test para enviar el formulario de registro POST
	// al ejecutar este test POST  1 vez tener el cuenta que a la segunda se deben cambiar los valores de registro
	//para evitar de que intenet guardar los mismo datos en la tabla y tire error


		@Test
	void testSubmitRegistrationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/registrar")
						.param("nombre", "TestUnitario")
						.param("correo", "testuni@hotmail.com")
						.param("contrasena", "testeo1")
						.param("tipo", "Estudiante"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/registro-exitoso"));
	}


    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


}
