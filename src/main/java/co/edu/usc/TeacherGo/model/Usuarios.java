package co.edu.usc.TeacherGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;



// con lombok no declaro manual los Get y Set

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor


public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String correo;

    private String contrasena;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

}
