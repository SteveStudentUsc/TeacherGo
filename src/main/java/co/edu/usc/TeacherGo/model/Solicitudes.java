package co.edu.usc.TeacherGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Solicitudes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuarios estudiante_id;

    @ManyToOne
    private Docentes docente_id;

    private String fecha_hora;

    private String estado;

}
