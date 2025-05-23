package co.edu.usc.TeacherGo.repository;

import co.edu.usc.TeacherGo.model.Solicitudes;
import co.edu.usc.TeacherGo.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitudes, Long> {

    List<Solicitudes> findByDocente(Usuarios docenteLogueado);
}
