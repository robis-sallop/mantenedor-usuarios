import type { User, UserFormData } from '../types';

const BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api';

const jsonHeaders = { 'Content-Type': 'application/json' };

export async function listUsers(): Promise<User[]> {
  const response = await fetch(`${BASE_URL}/users`);
  if (!response.ok) throw new Error('No se pudo cargar la lista de usuarios.');
  return response.json();
}

export async function createUser(payload: UserFormData): Promise<User> {
  const response = await fetch(`${BASE_URL}/users`, {
    method: 'POST',
    headers: jsonHeaders,
    body: JSON.stringify(payload)
  });
  if (!response.ok) throw new Error('No se pudo crear el usuario.');
  return response.json();
}

export async function updateUser(id: number, payload: UserFormData): Promise<User> {
  const response = await fetch(`${BASE_URL}/users/${id}`, {
    method: 'PUT',
    headers: jsonHeaders,
    body: JSON.stringify(payload)
  });
  if (!response.ok) throw new Error('No se pudo actualizar el usuario.');
  return response.json();
}

export async function deleteUser(id: number): Promise<void> {
  const response = await fetch(`${BASE_URL}/users/${id}`, {
    method: 'DELETE'
  });
  if (!response.ok) throw new Error('No se pudo eliminar el usuario.');
}
