package cl.previred.mantenedor.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * UserEntity representa la entidad de persistencia para los usuarios en la base de datos.
 * Mapea los campos de la tabla "users" y define las restricciones de unicidad.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "correo_electronico")})
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "rut")
    private Long rut;

    @Column(name = "dv")
    private String dv;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "correo_electronico", nullable = false, unique = true)
    private String correoElectronico;

    @Column(name = "contrasena")
    private String contrasena;
}
