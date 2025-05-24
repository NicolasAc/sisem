import React from 'react';
import { Dropdown } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function Topbar({ onToggleSidebar }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const isAdmin = user?.roles?.includes('ADMINISTRADOR');

    const getInitials = (username) => {
      if (!username) return 'US';
      return username
        .split(' ')
        .map((part) => part[0])
        .join('')
        .slice(0, 2)
        .toUpperCase();
    };
  console.log("User en topbar:", user);

  return (
    <nav className="navbar bg-white shadow-sm px-3 d-flex justify-content-between align-items-center">
      <div className="d-flex align-items-center gap-3">
         <button
           className="btn btn-outline-primary"
           onClick={onToggleSidebar}
           title="Toggle sidebar"> ☰ </button>

         <h2 className="mb-0 logo-sisem">SISEM</h2>
       </div>

      {/* Dropdown del usuario */}
      <Dropdown align="end">
        <Dropdown.Toggle
          variant="light"
          className="border rounded-circle d-flex align-items-center justify-content-center p-0"
          style={{ width: '40px', height: '40px' }}
          id="dropdown-user"
        >
          <strong>{getInitials(user?.username)}</strong>
        </Dropdown.Toggle>

        <Dropdown.Menu>
          <Dropdown.Item onClick={() => navigate('/perfil')}>Mi perfil</Dropdown.Item>
          {isAdmin && (
            <Dropdown.Item onClick={() => navigate('/usuarios')}>Usuarios</Dropdown.Item>
          )}
          <Dropdown.Divider />
          <Dropdown.Item onClick={logout}>Cerrar sesión</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </nav>
  );
}
