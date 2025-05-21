package co.edu.usc.TeacherGo.repository;

import co.edu.usc.TeacherGo.model.Solicitudes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitudes, Long> {
}
