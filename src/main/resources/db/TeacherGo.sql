CREATE DATABASE teachergo;
USE teachergo;


CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    tipo ENUM('Estudiante', 'Docente') NOT NULL

);


CREATE TABLE IF NOT EXISTS estado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    estado VARCHAR(100) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS docentes (
    id BIGINT PRIMARY KEY,
    calificacion_promedio DOUBLE DEFAULT 0,
    FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS docente_especialidades (
    docente_id BIGINT,
    especialidad VARCHAR(100),
    FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS docente_disponibilidad (
    docente_id BIGINT,
    horario VARCHAR(100),
    FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS solicitudes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT,
    docente_id BIGINT,
    fecha_hora VARCHAR(100) NOT NULL,
    estado ENUM('Pendiente', 'Aceptada', 'Rechazada') DEFAULT 'Pendiente',
    FOREIGN KEY (estudiante_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS valoraciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    docente_id BIGINT,
    estudiante_id BIGINT,
    puntuacion INT CHECK(puntuacion BETWEEN 1 AND 5),
    comentario TEXT,
    FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE,
    FOREIGN KEY (estudiante_id) REFERENCES usuarios(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    solicitud_id BIGINT,
    monto DECIMAL(10,2) NOT NULL,
    estado ENUM('Pendiente', 'Liberado', 'Reembolsado') DEFAULT 'Pendiente',
    FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id) ON DELETE CASCADE
);
