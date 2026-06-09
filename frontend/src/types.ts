export type User = {
  id: number;
  nombres: string;
  apellidos: string;
  rut: number;
  dv: string;
  fechaNacimiento: string;
  correoElectronico: string;
};

export type UserFormData = Omit<User, 'id'> & { id?: number; contrasena: string };
