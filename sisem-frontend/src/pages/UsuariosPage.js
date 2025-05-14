import React, { useEffect, useState } from 'react';
import { getUsuarios } from '../services/usuarioService';
import UsuarioTable from '../components/UsuarioTable';

const UsuariosPage = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUsuarios = async () => {
      try {
        const data = await getUsuarios();
        setUsuarios(data);
      } catch (err) {
        console.error("Error al cargar usuarios", err);
        setError("No se pudieron cargar los usuarios");
      }
    };

    fetchUsuarios();
  }, []);

  return (
    <div className="container-fluid">

      {error ? (
        <p className="text-danger">{error}</p>
      ) : (
        <UsuarioTable usuarios={usuarios} />
      )}
    </div>
  );
};

export default UsuariosPage;
