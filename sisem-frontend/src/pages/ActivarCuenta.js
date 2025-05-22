import { useEffect, useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function ActivarCuenta() {
  const [mensaje, setMensaje] = useState('🔄 Verificando el token...');
  const [exito, setExito] = useState(false);
  const [params] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const token = params.get('token');
    if (!token || token.trim() === '') {
      setMensaje('❌ Token no proporcionado');
      return; // Corta el efecto, no intenta activar
    }

    axios.get('/api/cuenta/activar', { params: { token } })
      .then(() => {
        setMensaje('✅ Cuenta activada correctamente. Redirigiendo al login...');
        setExito(true);
        setTimeout(() => navigate('/login'), 4000);
      })
      .catch(err => {
        if (err.response?.status === 401) {
          setMensaje('⚠️ El token expiró o no es válido.');
        } else if (err.response?.status === 404) {
          setMensaje('⚠️ Usuario no encontrado.');
        } else {
          setMensaje('❌ Error al activar la cuenta.');
        }
      });
  }, [params, navigate]);

  return (
    <div className="container text-center mt-5">
      <h2>{mensaje}</h2>
      {!exito && (
        <button className="btn btn-primary mt-3" onClick={() => navigate('/login')}>
          Ir al Login
        </button>
      )}
    </div>
  );
}
