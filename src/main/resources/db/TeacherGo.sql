CREATE DATABASE teachergo;
USE teachergo;

-- Tabla: usuarios
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


-- Tabla: docentes (hereda de usuarios por JOINED strategy)
CREATE TABLE IF NOT EXISTS docentes (
    id BIGINT PRIMARY KEY,
    calificacion_promedio DOUBLE DEFAULT 0,
    FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla: especialidades del docente
CREATE TABLE IF NOT EXISTS docente_especialidades (
    docente_id BIGINT,
    especialidad VARCHAR(100),
    FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE
);

-- Tabla: disponibilidad horaria
CREATE TABLE IF NOT EXISTS docente_disponibilidad (
    docente_id BIGINT,
    horario VARCHAR(100),
    FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE
);

-- Tabla: solicitudes
CREATE TABLE IF NOT EXISTS solicitudes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT,
    docente_id BIGINT,
    fecha_hora VARCHAR(100) NOT NULL,
    estado ENUM('Pendiente', 'Aceptada', 'Rechazada') DEFAULT 'Pendiente',
    FOREIGN KEY (estudiante_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE
);

-- Tabla: valoraciones
CREATE TABLE IF NOT EXISTS valoraciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    docente_id BIGINT,
    estudiante_id BIGINT,
    puntuacion INT CHECK(puntuacion BETWEEN 1 AND 5),
    comentario TEXT,
    FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE,
    FOREIGN KEY (estudiante_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla: pagos
CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    solicitud_id BIGINT,
    monto DECIMAL(10,2) NOT NULL,
    estado ENUM('Pendiente', 'Liberado', 'Reembolsado') DEFAULT 'Pendiente',
    FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id) ON DELETE CASCADE
);




-- insercion de datos para pruebas 
INSERT INTO usuarios (nombre, correo, contrasena, tipo) VALUES
('Ana Martínez', 'ana.martinez@example.com', 'pass1234', 'Estudiante'),
('Carlos López', 'carlos.lopez@example.com', 'securepass', 'Estudiante'),
('Laura Torres', 'laura.torres@example.com', 'laurapass', 'Docente'),
('Miguel Gómez', 'miguel.gomez@example.com', 'miguel123', 'Docente'),
('Paula Díaz', 'paula.diaz@example.com', 'paulapass', 'Docente');




INSERT INTO estado (usuario_id, estado)
VALUES
(1, 'escribe aqui'),
(1, 'prueba'),
(2, 'escribe aqui'),
(2, 'escribe aqui'),
(3, 'escribe aqui'),
(3, 'escribe aqui'),
(4, 'escribe aqui'),
(4, 'escribe aqui'),
(5, 'escribe aqui'),
(5, 'escribe aqui');


INSERT INTO docentes (id, calificacion_promedio) VALUES
(3, 4.7),
(4, 4.2),
(5, 4.8);


INSERT INTO docente_especialidades (docente_id, especialidad) VALUES
(3, 'Matemáticas'),
(3, 'Física'),
(4, 'Lengua y Literatura'),
(5, 'Biología'),
(5, 'Química');


INSERT INTO docente_disponibilidad (docente_id, horario) VALUES
(3, 'Lunes a Viernes, 10:00 - 12:00'),
(3, 'Sábados, 14:00 - 16:00'),
(4, 'Lunes a Miércoles, 16:00 - 18:00'),
(5, 'Martes y Jueves, 08:00 - 10:00'),
(5, 'Viernes, 12:00 - 14:00');


INSERT INTO solicitudes (estudiante_id, docente_id, fecha_hora, estado) VALUES
(1, 3, '2025-05-10 14:00', 'Pendiente'),
(2, 4, '2025-05-11 10:00', 'Aceptada'),
(1, 5, '2025-05-12 16:00', 'Rechazada'),
(2, 3, '2025-05-13 12:00', 'Aceptada'),
(1, 4, '2025-05-14 09:00', 'Pendiente');


INSERT INTO valoraciones (docente_id, estudiante_id, puntuacion, comentario) VALUES
(3, 1, 5, 'Excelente docente, explica muy bien.'),
(4, 2, 4, 'Muy buena clase.'),
(5, 1, 5, 'Me ayudó mucho con el tema.'),
(3, 2, 4, 'Buena atención.'),
(4, 1, 3, 'Podría mejorar su puntualidad.');


INSERT INTO pagos (solicitud_id, monto, estado) VALUES
(1, 150.00, 'Pendiente'),
(2, 200.00, 'Liberado'),
(3, 100.00, 'Reembolsado'),
(4, 180.00, 'Liberado'),
(5, 120.00, 'Pendiente');
