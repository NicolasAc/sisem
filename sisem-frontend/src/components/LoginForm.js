import React, { useState } from 'react';
import { login } from '../services/authService';

const LoginForm = ({ onLoginSuccess }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await login(username, password);
      localStorage.setItem('token', data.token);
      onLoginSuccess();
    } catch (err) {
      setError('Usuario o contraseña inválidos');
    }
  };

  return (
    <div className="d-flex justify-content-center mt-5">
      <form className="p-4 border rounded bg-light" onSubmit={handleSubmit} style={{ minWidth: 300 }}>
      <h1 className="text-center mb-2 display-4 fw-bold text-primary">SISEM</h1>
        <h2 className="text-center mb-4">Iniciar sesión</h2>
        <div className="mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Usuario"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <input
            type="password"
            className="form-control"
            placeholder="Contraseña"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        {error && <p className="text-danger">{error}</p>}
        <button type="submit" className="btn btn-primary w-100">Ingresar</button>
      </form>
    </div>
  );
};

export default LoginForm;
