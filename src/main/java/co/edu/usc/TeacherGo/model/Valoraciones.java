package co.edu.usc.TeacherGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Valoraciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int puntuacion;

    private String comentario;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docentes docente;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Usuarios estudiante;
}
