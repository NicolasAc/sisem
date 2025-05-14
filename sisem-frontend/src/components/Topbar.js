import React from 'react';
import { Dropdown } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Topbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const esAdmin = user?.roles?.includes("ADMINISTRADOR");

  return (
    <nav className="navbar navbar-light bg-light border-bottom px-4 w-100 d-flex justify-content-between">
      <h1 className="text-center mb-0 fw-bold text-primary">SISEM</h1>

      <Dropdown align="end">
        <Dropdown.Toggle variant="secondary" id="dropdown-user">
          {user?.username}
        </Dropdown.Toggle>

        <Dropdown.Menu>
          <Dropdown.Item onClick={() => navigate('/perfil')}>Mi perfil</Dropdown.Item>

          {esAdmin && (
            <Dropdown.Item onClick={() => navigate('/usuarios')}>Usuarios</Dropdown.Item>
          )}

          <Dropdown.Divider />
          <Dropdown.Item onClick={logout}>Cerrar sesión</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </nav>
  );
};

export default Topbar;
