import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const login = async (username, password) => {
  const response = await axios.post(`${API_URL}/login`, {
    username,
    password
  }, {
    withCredentials: true // para recibir cookies de backend
  });
  return response.data;
};

export const logout = async () => {
  return await axios.post(`${API_URL}/logout`, null, {
    withCredentials: true
  });
  };