import type { User } from '../types';

type Props = {
  users: User[];
  onEdit: (user: User) => void;
  onDelete: (user: User) => void;
};

export default function UserTable({ users, onEdit, onDelete }: Props) {
  if (users.length === 0) {
    return <p className="empty-state">No hay usuarios registrados aún.</p>;
  }

  return (
    <div className="table-container">
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>RUT</th>
            <th>Fecha de nacimiento</th>
            <th>Correo</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.nombres}</td>
              <td>{user.apellidos}</td>
              <td>{`${user.rut}-${user.dv}`}</td>
              <td>{new Date(user.fechaNacimiento).toLocaleDateString('es-CL')}</td>
              <td>{user.correoElectronico}</td>
              <td className="actions">
                <button type="button" onClick={() => onEdit(user)}>
                  Editar
                </button>
                <button type="button" className="danger" onClick={() => onDelete(user)}>
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
