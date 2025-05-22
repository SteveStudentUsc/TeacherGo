package co.edu.usc.TeacherGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "docentes")
public class Docentes extends Usuarios {

    private double calificacionPromedio;

    @ElementCollection
    @CollectionTable(name = "docente_especialidades", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "especialidad")
    private List<String> especialidades;

    @ElementCollection
    @CollectionTable(name = "docente_disponibilidad", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "horario")
    private List<String> disponibilidad;
}
