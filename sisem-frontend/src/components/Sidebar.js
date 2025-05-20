import React, { useState } from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import { BsHouse, BsPeople, BsFileText } from 'react-icons/bs';

const getItemStyle = (isActive, isHover, isExpanded) => ({
  display: 'flex',
  alignItems: 'center',
  justifyContent: isExpanded ? 'flex-start' : 'center',
  padding: '0.6rem',
  marginBottom: '0.3rem',
  color: isActive || isHover ? 'white' : '#bbb',
  backgroundColor: isActive || isHover ? 'rgba(255,255,255,0.1)' : 'transparent',
  borderRadius: '6px',
  fontWeight: isActive ? 'bold' : 'normal',
  fontSize: '1rem',
  textDecoration: 'none',
  cursor: 'pointer',
  transition: 'background-color 0.2s ease, color 0.2s ease',
});

const getTextStyle = (isExpanded) => ({
  fontSize: '1rem',
  maxWidth: isExpanded ? '200px' : '0px',
  opacity: isExpanded ? 1 : 0,
  whiteSpace: 'nowrap',
  overflow: 'hidden',
  textOverflow: 'ellipsis',
  display: 'inline-block',
  transition: 'all 0.3s ease',
  flexShrink: 1,
  minWidth: 0,
  marginLeft: isExpanded ? '0.75rem' : '0',
});

export default function Sidebar({ isCollapsed, isMobile }) {
  const [isHovered, setIsHovered] = useState(false);
  const [hoveredItem, setHoveredItem] = useState(null);
  const location = useLocation();

  // 👇 Esta lógica es la que arregla todo
  const isExpanded = isMobile ? !isCollapsed : isHovered || !isCollapsed;

  const containerStyle = {
    width: isMobile
      ? isCollapsed
        ? '0px'
        : '200px'
      : isHovered || !isCollapsed
      ? '200px'
      : '48px',
    transition: 'width 0.3s ease',
    overflow: 'hidden',
    height: '100%',
  };

  return (
    <nav
      className="bg-dark d-flex flex-column text-white"
      style={containerStyle}
      onMouseEnter={() => !isMobile && setIsHovered(true)}
      onMouseLeave={() => !isMobile && setIsHovered(false)}
    >
      <ul className="list-unstyled m-0 p-0">
        {menuItems.map((item, i) => {
          const isActive = location.pathname.startsWith(item.path);
          const isHover = hoveredItem === item.title;

          return (
            <li key={i}>
              <NavLink
                to={item.path}
                style={getItemStyle(isActive, isHover, isExpanded)}
                onMouseEnter={() => setHoveredItem(item.title)}
                onMouseLeave={() => setHoveredItem(null)}
              >
                {item.icon}
                <span style={getTextStyle(isExpanded)}>{item.title}</span>
              </NavLink>
            </li>
          );
        })}
      </ul>
    </nav>
  );
}

const menuItems = [
  { title: 'Dashboard', path: '/dashboard', icon: <BsHouse size={20} /> },
  { title: 'Personas', path: '/personas', icon: <BsPeople size={20} /> },
  { title: 'Registros', path: '/registros', icon: <BsFileText size={20} /> },
];