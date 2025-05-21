package co.edu.usc.TeacherGo.model;

import lombok.Getter;
import lombok.Setter;
// solo para obtencion de los datos no necesito mapeo
@Getter
@Setter
public class Login {
    private String correo;
    private String contrasena;
}
