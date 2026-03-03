-- Create all tables for ForoHub

-- Table: curso
CREATE TABLE curso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: usuario
CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: perfil
CREATE TABLE perfil (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: usuario_perfil (many-to-many)
CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (perfil_id) REFERENCES perfil(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: topico
CREATE TABLE topico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE,
    UNIQUE (titulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: respuesta
CREATE TABLE respuesta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
    topico_id BIGINT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    autor_id BIGINT NOT NULL,
    solucion BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (topico_id) REFERENCES topico(id) ON DELETE CASCADE,
    FOREIGN KEY (autor_id) REFERENCES usuario(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert default profiles
INSERT INTO perfil (nombre) VALUES 
('ROLE_USER'),
('ROLE_ADMIN');

-- Insert default course
INSERT INTO curso (nombre, categoria) 
VALUES ('Java Spring Boot', 'Backend');
