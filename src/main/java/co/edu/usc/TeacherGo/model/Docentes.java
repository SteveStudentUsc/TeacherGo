package co.edu.usc.TeacherGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "docentes")
@Getter
@Setter
public class Docentes extends Usuarios {

    @Column(name = "calificacion_promedio")
    private double calificacionPromedio = 0.0;
}
