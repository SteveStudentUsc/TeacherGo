package co.edu.usc.TeacherGo.repository;

import co.edu.usc.TeacherGo.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByCorreoAndContrasena(String correo, String contrasena);
}
