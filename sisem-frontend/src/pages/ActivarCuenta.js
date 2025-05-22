import React, { useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { activarCuenta } from '../services/usuarioService';

const ActivarCuenta = () => {
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const [password, setPassword] = useState('');
  const [estado, setEstado] = useState(null);
  const navigate = useNavigate();

  const handleActivar = async () => {
    try {
      const resultado = await activarCuenta({ token, password });
      setEstado({ tipo: 'ok', mensaje: resultado });
      // Podés redirigir después de un segundo
      setTimeout(() => navigate('/login'), 2000);
    } catch (error) {
      const data = error.response?.data;

      let mensajeFormateado;

      if (typeof data === 'string') {
        mensajeFormateado = data;
      } else if (typeof data === 'object' && data !== null) {
        mensajeFormateado = Object.values(data);
      } else {
        mensajeFormateado = 'Error inesperado';
      }

      setEstado({ tipo: 'error', mensaje: mensajeFormateado });
    }
  };

  return (
    <div className="container mt-5">
      <h2>Activar Cuenta</h2>

      {estado && (
        <div className={`alert alert-${estado.tipo === 'ok' ? 'success' : 'danger'}`}>
          {Array.isArray(estado.mensaje)
            ? estado.mensaje.map((msg, i) => <div key={i}>{msg}</div>)
            : estado.mensaje}
        </div>
      )}

      <input
        type="password"
        className="form-control mb-3"
        placeholder="Nueva contraseña"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button className="btn btn-primary" onClick={handleActivar}>
        Activar
      </button>
    </div>
  );
};

export default ActivarCuenta;
