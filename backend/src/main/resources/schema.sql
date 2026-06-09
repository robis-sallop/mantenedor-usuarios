CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombres VARCHAR(255),
  apellidos VARCHAR(255),
  rut BIGINT,
  dv VARCHAR(10),
  fecha_nacimiento DATE,
  correo_electronico VARCHAR(255) NOT NULL UNIQUE,
  contrasena VARCHAR(255)
);

