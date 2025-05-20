
import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const getUsuarios = async () => {
  const response = await axios.get(`${API_URL}/api/usuarios`, {
    withCredentials: true // asegura que se mande la cookie
  });
  console.log("getUsuarios:", response.data);
  return response.data; // lista de usuarios
};

export const crearUsuario = async (usuario) => {
  const response = await axios.post(`${API_URL}/api/usuarios`, usuario, {
    withCredentials: true
  });
  return response.data;
};
