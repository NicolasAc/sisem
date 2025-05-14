import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import { BsHouse, BsPeople, BsFileText } from 'react-icons/bs';

const Sidebar = () => {
  const [openMenus, setOpenMenus] = useState({});

  const toggleMenu = (title) => {
    setOpenMenus(prev => ({ ...prev, [title]: !prev[title] }));
  };

  const menuItems = [
    {
      title: "Dashboard",
      path: "/dashboard",
      icon: <BsHouse />,
    },
    {
      title: "Personas",
      icon: <BsPeople />,
      children: [
        { title: "Listar Personas", path: "/personas" }
      ]
    },
    {
      title: "Registros",
      icon: <BsFileText />,
      children: [
        { title: "Formulario Ingreso", path: "/registros/formulario-ingreso" },
        { title: "Ingresar Registros", path: "/registros/nuevo" }
      ]
    }
  ];

  return (
    <nav className="bg-dark text-white p-3" style={{ width: '240px', minHeight: '100vh' }}>
      <h4 className="text-center text-white fw-bold mb-4">SISEM</h4>
      <ul className="list-unstyled">
        {menuItems.map((item, i) => (
          <li key={i}>
            {item.children ? (
              <>
                <button
                  className="btn btn-dark w-100 text-start mb-1"
                  onClick={() => toggleMenu(item.title)}
                >
                  {item.icon} <span className="ms-2">{item.title}</span>
                </button>
                {openMenus[item.title] && (
                  <ul className="list-unstyled ps-3">
                    {item.children.map((sub, j) => (
                      <li key={j}>
                        <NavLink
                          to={sub.path}
                          className={({ isActive }) =>
                            `nav-link text-white py-1 ${isActive ? 'fw-bold' : ''}`
                          }
                        >
                          {sub.title}
                        </NavLink>
                      </li>
                    ))}
                  </ul>
                )}
              </>
            ) : (
              <NavLink
                to={item.path}
                className={({ isActive }) =>
                  `nav-link text-white mb-1 ${isActive ? 'fw-bold' : ''}`
                }
              >
                {item.icon} <span className="ms-2">{item.title}</span>
              </NavLink>
            )}
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default Sidebar;
