import React from 'react';
import { useAuth } from '../context/AuthContext';

const DashboardPage = () => {
  const { user } = useAuth();

  return (
    <div>
      <h2>Bienvenido, {user?.username}</h2>
      <p>Roles: {user?.roles?.join(', ')}</p>
    </div>
  );
};

export default DashboardPage;