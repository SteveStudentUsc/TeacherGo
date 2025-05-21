package co.edu.usc.TeacherGo.repository;

import co.edu.usc.TeacherGo.model.Valoraciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ValoracionRepository extends JpaRepository<Valoraciones, Long> {
    @Query("SELECT v FROM Valoraciones v WHERE v.docente.id = :docenteId")
    List<Valoraciones> findByDocenteId(@org.springframework.data.repository.query.Param("docenteId") Long docenteId);

}


