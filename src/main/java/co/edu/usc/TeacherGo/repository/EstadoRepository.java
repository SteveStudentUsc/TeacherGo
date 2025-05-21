package co.edu.usc.TeacherGo.repository;

import co.edu.usc.TeacherGo.model.Estado;
import co.edu.usc.TeacherGo.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {


    List<Estado> findByUsuario(Usuarios usuarioActual);
}