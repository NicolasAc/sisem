import React from 'react';
import { Navigate } from 'react-router-dom';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import UsuariosPage from './pages/UsuariosPage';
import UsuarioNuevoPage from './pages/UsuarioNuevoPage';
import ActivarCuenta from './pages/ActivarCuenta';


import Layout from './layout/Layout';
import PrivateRoute from './routes/PrivateRoute';

function App() {
  return (
<Routes>
    {/* Rutas públicas */}
    <Route path="/login" element={<LoginPage />} />
    <Route path="activar" element={<ActivarCuenta />} />

    {/* Rutas protegidas */}
    <Route
      path="/"
      element={
        <PrivateRoute>
          <Layout />

        </PrivateRoute>
      }
    >

      <Route path="dashboard" element={<DashboardPage />} />
      <Route path="personas" element={<DashboardPage />} />
      <Route path="usuarios" element={<UsuariosPage />} />

      <Route path="/usuarios/nuevo" element={<UsuarioNuevoPage />} />
      {/* Podés agregar más rutas anidadas acá */}
    </Route>

    {/* Catch-all */}
    <Route path="*" element={<Navigate to="/login" />} />
  </Routes>
);
}

export default App;
