import React, { useState } from 'react';
import DataTable from 'react-data-table-component';
import { useNavigate } from 'react-router-dom';
import { BsPencilSquare } from 'react-icons/bs';

const UsuarioTable = ({ usuarios }) => {
  const [filtro, setFiltro] = useState('');
  const navigate = useNavigate();

  const columnas = [
    { name: 'Usuario', selector: row => row.username, sortable: true },
      { name: 'N° CCJPU', selector: row => row.nroCcjpu || '-', sortable: true },
    { name: 'Nombre', selector: row => row.nombre, sortable: true },
    { name: 'Apellido', selector: row => row.apellido, sortable: true },
    { name: 'Email', selector: row => row.email, sortable: true },
    { name: 'Eliminado', selector: row => row.activo ? 'NO' : 'SI', sortable: true },
     {
       name: 'Acciones',
       center: true, // Centra el contenido de la celda
       cell: row => (
         <button
           className="btn p-0 border-0 bg-transparent"
           onClick={() => editarUsuario(row.id)}
           title="Editar"
         >
           <BsPencilSquare size={20} style={{ color: '#0d6efd' }} />
         </button>
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
        <input
          type="text"
          placeholder="Buscar..."
          onChange={(e) => setFiltro(e.target.value)}
          className="form-control w-25"
        />
      }
      noDataComponent={<span className="text-muted">No se encontraron usuarios</span>}
    />
  );
};

export default UsuarioTable;
