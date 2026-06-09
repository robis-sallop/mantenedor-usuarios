import { useEffect, useState } from 'react';
import type { User, UserFormData } from '../types';

type Props = {
  onSubmit: (form: UserFormData) => Promise<void>;
  onCancel: () => void;
  initialData?: User;
};

const emptyForm = {
  nombres: '',
  apellidos: '',
  rut: 0,
  dv: '',
  fechaNacimiento: '',
  correoElectronico: '',
  contrasena: ''
};

export default function UserForm({ onSubmit, onCancel, initialData }: Props) {
  const [form, setForm] = useState<UserFormData>(emptyForm);
  const [rutInput, setRutInput] = useState('');
  const [rutError, setRutError] = useState<string | null>(null);
  const [dateError, setDateError] = useState<string | null>(null);
  const [dateInput, setDateInput] = useState('');

  useEffect(() => {
    if (initialData) {
      setForm({
        id: initialData.id,
        nombres: initialData.nombres,
        apellidos: initialData.apellidos,
        rut: initialData.rut,
        dv: initialData.dv,
        fechaNacimiento: initialData.fechaNacimiento,
        correoElectronico: initialData.correoElectronico,
        contrasena: ''
      });
      setRutInput(`${initialData.rut}-${initialData.dv}`);
      if (initialData.fechaNacimiento) {
        const iso = initialData.fechaNacimiento; 
        const parts = iso.split('-');
        if (parts.length === 3) setDateInput(`${parts[2]}-${parts[1]}-${parts[0]}`);
      }
    } else {
      setForm(emptyForm);
      setRutInput('');
    }
  }, [initialData]);

  const handleChange = (field: keyof UserFormData, value: string) => {
    setForm((current) => ({
      ...current,
      [field]: field === 'rut' ? Number(value) : value
    }));
  };

  const cleanRutRaw = (raw: string) => raw.replace(/\./g, '').replace(/\s/g, '').toUpperCase();

  const formatRut = (raw: string) => {
    const cleaned = cleanRutRaw(raw).replace(/-/g, '');
    if (cleaned.length === 0) return '';
    if (cleaned.length === 1) return cleaned; 
    const digits = cleaned.slice(0, -1);
    const dv = cleaned.slice(-1);
    return `${digits}-${dv}`;
  };

  const calcDv = (rutNumber: string) => {
    const nums = rutNumber.replace(/\D/g, '');
    let sum = 0;
    let mul = 2;
    for (let i = nums.length - 1; i >= 0; i--) {
      sum += Number(nums[i]) * mul;
      mul = mul === 7 ? 2 : mul + 1;
    }
    const res = 11 - (sum % 11);
    if (res === 11) return '0';
    if (res === 10) return 'K';
    return String(res);
  };

  const validateRut = (value: string) => {
    const cleaned = cleanRutRaw(value).replace(/-/g, '');
    if (cleaned.length < 2) return false;
    const digits = cleaned.slice(0, -1);
    const dv = cleaned.slice(-1);
    if (!/^\d+$/.test(digits)) return false;
    const expected = calcDv(digits);
    return expected === dv;
  };

  const handleRutInput = (value: string) => {
    const cleaned = cleanRutRaw(value);
    const formatted = formatRut(cleaned);
    setRutInput(formatted);

    const withoutDash = formatted.replace(/-/g, '');
    if (withoutDash.length >= 2) {
      const digits = withoutDash.slice(0, -1);
      const dv = withoutDash.slice(-1);
      setForm((current) => ({ ...current, rut: Number(digits), dv }));
      if (validateRut(formatted)) {
        setRutError(null);
      } else {
        setRutError('RUT inválido');
      }
    } else {
      setForm((current) => ({ ...current, rut: 0, dv: '' }));
      setRutError('RUT incompleto');
    }
  };

  const submit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!validateRut(rutInput)) {
      setRutError('RUT inválido');
      return;
    }
    if (!validateFechaNacimiento(dateInput)) {
      setDateError('Fecha de nacimiento inválida');
      return;
    }
    await onSubmit(form);
  };
  const today = new Date();

  const isoFromDisplay = (display: string) => {
    const m = display.match(/^(\d{1,2})-(\d{1,2})-(\d{4})$/);
    if (!m) return '';
    const day = Number(m[1]);
    const month = Number(m[2]);
    const year = Number(m[3]);
    if (year < 1900 || year > today.getFullYear()) return '';
    const date = new Date(year, month - 1, day);
    if (date.getFullYear() !== year || date.getMonth() + 1 !== month || date.getDate() !== day) return '';
    const iso = `${String(year).padStart(4, '0')}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    if (iso > today.toISOString().slice(0, 10)) return '';
    return iso;
  };

  const formatDateDisplay = (raw: string) => {
    const digits = raw.replace(/\D/g, '').slice(0, 8); 
    if (digits.length <= 2) return digits;
    if (digits.length <= 4) return `${digits.slice(0, 2)}-${digits.slice(2)}`;
    return `${digits.slice(0, 2)}-${digits.slice(2, 4)}-${digits.slice(4)}`;
  };

  const validateFechaNacimiento = (display: string) => {
    return isoFromDisplay(display) !== '';
  };

  const handleFechaChange = (value: string) => {
    const formatted = formatDateDisplay(value);
    setDateInput(formatted);
    const iso = isoFromDisplay(formatted);
    if (iso) {
      setForm((current) => ({ ...current, fechaNacimiento: iso }));
      setDateError(null);
    } else {
      setForm((current) => ({ ...current, fechaNacimiento: '' }));
      setDateError('Fecha inválida');
    }
  };

  return (
    <form className="user-form" onSubmit={submit}>
      <div className="form-row">
        <label>
          Nombres
          <input
            value={form.nombres}
            onChange={(event) => handleChange('nombres', event.target.value)}
            required
          />
        </label>
        <label>
          Apellidos
          <input
            value={form.apellidos}
            onChange={(event) => handleChange('apellidos', event.target.value)}
            required
          />
        </label>
      </div>

      <div className="form-row">
        <label>
          RUT (formato 12345678-5)
          <input
            type="text"
            inputMode="latin"
            value={rutInput}
            onChange={(event) => handleRutInput(event.target.value)}
            onBlur={(e) => setRutInput(formatRut(e.target.value))}
            onInvalid={(e) => (e.currentTarget as HTMLInputElement).setCustomValidity(rutError ?? 'RUT inválido')}
            onInput={(e) => (e.currentTarget as HTMLInputElement).setCustomValidity('')}
            placeholder="12345678-5"
            required
          />
         {rutError && <div style={{ color: 'red', marginTop: 6 }}>{rutError}</div>}
        </label>
      </div>

      <div className="form-row">
        <label>
          Fecha de nacimiento (dd-mm-yyyy)
          <input
            type="text"
            inputMode="numeric"
            placeholder="dd-mm-yyyy"
            value={dateInput}
            onChange={(event) => handleFechaChange(event.target.value)}
            onBlur={(e) => handleFechaChange(e.target.value)}
            onInvalid={(e) => (e.currentTarget as HTMLInputElement).setCustomValidity(dateError ?? 'Fecha inválida (dd-mm-yyyy)')}
            onInput={(e) => (e.currentTarget as HTMLInputElement).setCustomValidity('')}
            required
          />
          {dateError && <div style={{ color: 'red', marginTop: 6 }}>{dateError}</div>}
        </label>
        <label>
          Correo electrónico
          <input
            type="email"
            value={form.correoElectronico}
            onChange={(event) => handleChange('correoElectronico', event.target.value)}
            required
          />
        </label>
      </div>

      <div className="form-row">
        <label>
          Contraseña
          <input
            type="password"
            value={form.contrasena}
            onChange={(event) => handleChange('contrasena', event.target.value)}
            required
            placeholder={initialData ? 'Reingresa la contraseña' : ''}
          />
        </label>
      </div>

      <div className="form-actions">
        <button type="submit">{initialData ? 'Actualizar usuario' : 'Crear usuario'}</button>
        <button type="button" className="secondary" onClick={onCancel}>
          Cancelar
        </button>
      </div>
    </form>
  );
}
