import React, { useState, useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';

export default function Layout() {
  const [isCollapsed, setIsCollapsed] = useState(false);
  const [isMobile, setIsMobile] = useState(window.innerWidth < 768);

  //Setea el tamaño, establece la funcion handleResize y un listener para llamarlo si cambia el tamaño del nav
  useEffect(() => {
    const handleResize = () => setIsMobile(window.innerWidth < 768);
    handleResize();
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const toggleSidebar = () => setIsCollapsed(prev => !prev);//renderizar al cambiar isCollapsed

  return (
    <div className="d-flex flex-column vh-100">
      <Topbar onToggleSidebar={toggleSidebar} />
      <div className="d-flex flex-grow-1 w-100 overflow-hidden">
        <Sidebar isCollapsed={isCollapsed} isMobile={isMobile} />
        <main
          className="flex-grow-1 bg-light"
          style={{
            padding: '1rem',
            overflowY: 'auto',
            maxHeight: '100vh'
          }}
        >
          <Outlet />
        </main>
      </div>
    </div>
  );
}
