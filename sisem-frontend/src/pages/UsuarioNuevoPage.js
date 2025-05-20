import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { crearUsuario } from '../services/usuarioService';

const ROLES_DISPONIBLES = [
  { id: 1, nombre: 'Administrador' },
  { id: 2, nombre: 'Administrativo' },
  { id: 10, nombre: 'Auxiliar Enefermeria' },
  { id: 7, nombre: 'Licenciado' },
  { id: 9, nombre: 'Operador Laboral' },
  { id: 11, nombre: 'Otro' },
  { id: 4, nombre: 'Psicólogo' },
  { id: 3, nombre: 'Psiquíatra' },
  { id: 8, nombre: 'Referente de Convivencia' },
  { id: 6, nombre: 'Tallerista' },
  { id: 5, nombre: 'Trabajador Social' },
];

const UsuarioNuevoPage = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: '',
    nombre: '',
    apellido: '',
    email: '',
    funcion: '',
    nroCcjpu: '',
    roles: []
  });

  const [errors, setErrors] = useState({});
  const [serverError, setServerError] = useState('');

  const [successMessage, setSuccessMessage] = useState('');

  const validar = () => {
    const nuevosErrores = {};
    if (!form.username.trim()) nuevosErrores.username = 'El nombre de usuario es obligatorio';
    if (!form.nombre.trim()) nuevosErrores.nombre = 'El nombre es obligatorio';
    if (!form.apellido.trim()) nuevosErrores.apellido = 'El apellido es obligatorio';
    if (!form.email.trim()) nuevosErrores.email = 'El email es obligatorio';
    if (!form.funcion.trim()) nuevosErrores.funcion = 'La función es obligatoria';
    if (!form.nroCcjpu.trim()) nuevosErrores.nroCcjpu = 'El N° CCJPU es obligatorio';
    if (form.roles.length === 0) nuevosErrores.roles = 'Debes seleccionar al menos un rol';

    setErrors(nuevosErrores);
    return Object.keys(nuevosErrores).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
    setErrors(prev => ({ ...prev, [name]: '' }));
  };

  const handleRoleToggle = (nombreRol) => {
    setForm(prev => {
      const nuevosRoles = prev.roles.includes(nombreRol)
        ? prev.roles.filter(r => r !== nombreRol)
        : [...prev.roles, nombreRol];
      return { ...prev, roles: nuevosRoles };
    });
    setErrors(prev => ({ ...prev, roles: '' }));
  };

const handleSubmit = async (e) => {
  e.preventDefault();
  setServerError('');

  if (!validar()) return;

  try {
    await crearUsuario(form); // ✅ usamos el servicio
    setSuccessMessage('Usuario creado correctamente');
    setErrors({});
    setForm({
      username: '',
      nombre: '',
      apellido: '',
      email: '',
      funcion: '',
      nroCcjpu: '',
      roles: []
    });

  } catch (err) {
    if (err.response?.status === 409) {
      setServerError(err.response.data || 'El usuario ya existe');
    } else if (err.response?.status === 400) {
      setServerError('Uno o más roles no son válidos');
    } else {
      setServerError('Error inesperado al crear el usuario');
    }
  }
};

  return (
    <div className="container mt-4 d-flex justify-content-center ">
      <div className="w-100 bg-white p-4 rounded shadow-sm" style={{ maxWidth: '600px' }}>
        <h2 className="fw-bold mb-4">Nuevo Usuario</h2>

        {serverError && <div className="alert alert-danger">{serverError}</div>}
        {successMessage && (
          <div className="alert alert-success">{successMessage}</div>
        )}
        <form onSubmit={handleSubmit} noValidate>
          {['username', 'nombre', 'apellido', 'email', 'funcion', 'nroCcjpu'].map((campo) => (
            <div className="mb-3" key={campo}>
              <input
                name={campo}
                type={campo === 'email' ? 'email' : 'text'}
                placeholder={
                  campo === 'username' ? 'Nombre de Usuario' :
                  campo === 'nroCcjpu' ? 'N° CCJPU' :
                  campo === 'funcion' ? 'Función' :
                  campo[0].toUpperCase() + campo.slice(1)
                }
                value={form[campo]}
                onChange={handleChange}
                className={`form-control ${errors[campo] ? 'is-invalid' : ''}`}
              />
              {errors[campo] && (
                <div className="invalid-feedback">{errors[campo]}</div>
              )}
            </div>
          ))}

          <div className="mb-3">
            <label className="form-label">Roles</label>
            <div className="border rounded p-2">
              {ROLES_DISPONIBLES.map(({ id, nombre }) => (
                <div className="form-check" key={id}>
                  <input
                    className="form-check-input"
                    type="checkbox"
                    id={`rol-${id}`}
                    checked={form.roles.includes(nombre)}
                    onChange={() => handleRoleToggle(nombre)}
                  />
                  <label className="form-check-label" htmlFor={`rol-${id}`}>
                    {nombre}
                  </label>
                </div>
              ))}
            </div>
            {errors.roles && (
              <div className="text-danger mt-1">{errors.roles}</div>
            )}
          </div>

          <div className="d-flex">
            <button type="submit" className="btn btn-primary me-2">
              Crear Usuario
            </button>
            <button
              type="button"
              className="btn btn-secondary"
              onClick={() => navigate('/usuarios')}
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UsuarioNuevoPage;
