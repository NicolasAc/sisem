import React, { useState } from 'react';
import DataTable from 'react-data-table-component';
import { useNavigate } from 'react-router-dom';
import { BsPencilSquare , BsPlusCircle,BsPersonXFill,BsEnvelopeArrowUpFill,BsKeyFill  } from 'react-icons/bs';

const UsuarioTable = ({ usuarios }) => {
  const [filtro, setFiltro] = useState('');
  const navigate = useNavigate();

    const capitalizar = (texto) =>
      texto.charAt(0).toUpperCase() + texto.slice(1).toLowerCase();


  const toggleEstadoUsuario = (usuario) => {
    // Lógica para cambiar el estado del usuario (activo/deshabilitado)
    console.log(`Cambiar estado del usuario con ID ${usuario.id}`);
    // Ejemplo: await usuarioService.toggleEstado(usuario.id);
  };

  const reenviarCorreoActivacion = (id) => {
    // Lógica para reenviar el correo
    console.log(`Reenviar correo de activación al usuario con ID ${id}`);
    // Ejemplo: await usuarioService.reenviarCorreo(id);
  };

  const confirmarCambioPassword = (usuario) => {
    const confirmado = window.confirm(
      `Se enviará un enlace a ${usuario.email} para que ${usuario.nombre} ${usuario.apellido} pueda establecer una nueva contraseña. ¿Desea continuar con el envío del correo?`
    );

    if (confirmado) {
      reenviarCorreoActivacion(usuario);
    }
  };

  const columnas = [
    { name: 'Usuario', selector: row => row.username, sortable: true },
      { name: 'N° CCJPU', selector: row => row.nroCcjpu || '-', sortable: true },
    { name: 'Nombre', selector: row => row.nombre, sortable: true },
    { name: 'Apellido', selector: row => row.apellido, sortable: true },
    { name: 'Email', selector: row => row.email, sortable: true },
    { name: 'Eliminado', selector: row => capitalizar(row.estado), sortable: true },
    {
      name: 'Acciones',
      center: true,
      cell: row => (
        <div className="d-flex justify-content-center align-items-center gap-2">
          {/* Editar */}
          <button
            className="btn p-0 border-0 bg-transparent"
            onClick={() => editarUsuario(row.id)}
            title="Editar"
          >
            <BsPencilSquare size={20} style={{ color: '#0d6efd' }} />
          </button>

          {/* Deshabilitar/Habilitar */}
          <button
            className="btn p-0 border-0 bg-transparent"
            onClick={() => toggleEstadoUsuario(row)}
            title={row.estado.toLowerCase() === 'activo' ? 'Deshabilitar' : 'Habilitar'}
          >
            <BsPersonXFill
              size={20}
              style={{ color: row.estado.toLowerCase() === 'activo'? '#dc3545' : '#28a745' }}
            />
          </button>

            {/* Cambio de contraseña */}
            <button
              className="btn p-0 border-0 bg-transparent"
              onClick={() => confirmarCambioPassword(row)}
              title="Enviar enlace para cambio de contraseña"
            >
              <BsKeyFill size={20} style={{ color: '#ffc107' }} />
            </button>

        </div>
      ),
      ignoreRowClick: true,
      allowOverflow: true,
      button: true,
    }
  ];

    const editarUsuario = (id) => {
      navigate(`/usuarios/editar/${id}`);
    };


  const datosFiltrados = usuarios.filter(
    u =>
          (u.username || '').toLowerCase().includes(filtro.toLowerCase()) ||
          (u.nombre || '').toLowerCase().includes(filtro.toLowerCase()) ||
          (u.apellido || '').toLowerCase().includes(filtro.toLowerCase()) ||
          (u.email || '').toLowerCase().includes(filtro.toLowerCase()) ||
          (u.nroCcjpu || '').toString().toLowerCase().includes(filtro.toLowerCase())
  );

  return (
    <DataTable
      title="Usuarios"
      columns={columnas}
      data={datosFiltrados}
      pagination
      highlightOnHover
      responsive
      subHeader
      paginationComponentOptions={{
        rowsPerPageText: 'Filas por página',
        rangeSeparatorText: 'de',
        noRowsPerPage: false,
        selectAllRowsItem: false,
      }}
      subHeaderComponent={
 <div className="d-flex justify-content-between align-items-center w-100">
    <input
      type="text"
      placeholder="Buscar..."
      onChange={(e) => setFiltro(e.target.value)}
      className="form-control w-25"
    />
    <button
      className="btn btn-outline-success d-flex align-items-center"
      onClick={() => navigate('/usuarios/nuevo')}
    >
      <BsPlusCircle className="me-2" size={18} />
      Nuevo usuario
    </button>
  </div>
      }
      noDataComponent={<span className="text-muted">No se encontraron usuarios</span>}
    />
  );
};

export default UsuarioTable;
