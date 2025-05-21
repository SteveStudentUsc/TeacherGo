package co.edu.usc.TeacherGo.repository;

import co.edu.usc.TeacherGo.model.Docentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Docentes, Long> {
}
