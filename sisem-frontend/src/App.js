import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';

function App() {
  return (
 <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        {/* Agregaremos más rutas luego */}
      </Routes>
    </Router>
  );
}

export default App;
