import { useEffect, useState } from 'react';
import type { User, UserFormData } from './types';
import { createUser, deleteUser, listUsers, updateUser } from './services/userService';
import UserTable from './components/UserTable';
import UserForm from './components/UserForm';

function App() {
  const [users, setUsers] = useState<User[]>([]);
  const [selectedUser, setSelectedUser] = useState<User | undefined>(undefined);
  const [message, setMessage] = useState<string>('');
  const [loading, setLoading] = useState(false);

  const loadUsers = async () => {
    setLoading(true);
    try {
      const data = await listUsers();
      setUsers(data);
    } catch (error) {
      setMessage(error instanceof Error ? error.message : 'Error al cargar usuarios.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void loadUsers();
  }, []);

  const onSubmit = async (form: UserFormData) => {
    setLoading(true);
    try {
      if (form.id) {
        await updateUser(form.id, form);
        setMessage('Usuario actualizado correctamente.');
      } else {
        await createUser(form);
        setMessage('Usuario creado correctamente.');
      }
      setSelectedUser(undefined);
      await loadUsers();
    } catch (error) {
      setMessage(error instanceof Error ? error.message : 'Error en la operación.');
    } finally {
      setLoading(false);
    }
  };

  const onDelete = async (user: User) => {
    if (!window.confirm(`¿Eliminar al usuario ${user.nombres} ${user.apellidos}?`)) {
      return;
    }
    setLoading(true);
    try {
      await deleteUser(user.id);
      setMessage('Usuario eliminado correctamente.');
      await loadUsers();
      if (selectedUser?.id === user.id) {
        setSelectedUser(undefined);
      }
    } catch (error) {
      setMessage(error instanceof Error ? error.message : 'Error al eliminar usuario.');
    } finally {
      setLoading(false);
    }
  };

  const onEdit = (user: User) => {
    setSelectedUser(user);
    setMessage('');
  };

  const onCancel = () => {
    setSelectedUser(undefined);
    setMessage('');
  };

  return (
    <div className="app-shell">
      <header>
        <div>
          <h1>Mantenedor de Usuarios</h1>
          <p>Frontend React + TypeScript consumiendo la API Spring Boot.</p>
        </div>
      </header>

      <main>
        <section className="panel">
          <div className="panel-header">
            <h2>{selectedUser ? 'Editar usuario' : 'Crear nuevo usuario'}</h2>
          </div>
          <UserForm onSubmit={onSubmit} onCancel={onCancel} initialData={selectedUser} />
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Usuarios registrados</h2>
          </div>
          {message && <div className="notification">{message}</div>}
          {loading ? <p>Cargando...</p> : <UserTable users={users} onEdit={onEdit} onDelete={onDelete} />}
        </section>
      </main>
    </div>
  );
}

export default App;
