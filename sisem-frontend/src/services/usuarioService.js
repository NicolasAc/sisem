// src/services/usuarioService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const getUsuarios = async () => {
  const response = await axios.get(`${API_URL}/api/usuarios`, {
    withCredentials: true // asegura que se mande la cookie
  });
  return response.data; // lista de usuarios
};
