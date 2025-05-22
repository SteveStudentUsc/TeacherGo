package co.edu.usc.TeacherGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Solicitudes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaHora;

    private String estado; // 'Pendiente', 'Aceptada', 'Rechazada'

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Usuarios estudiante;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docentes docente;
}
