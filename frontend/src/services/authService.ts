import axios from 'axios';
import type { LoginRequest, RegisterRequest } from '../types';

const API_URL = 'http://localhost:8080';

const login = async (data: LoginRequest) => {
  const response = await axios.post(`${API_URL}/login`, data);
  if (response.data.token) {
    localStorage.setItem('token', JSON.stringify(response.data.token));
  }
  return response.data;
};

const register = async (data: RegisterRequest) => {
  const response = await axios.post(`${API_URL}/users/save`, data);
  return response.data;
};

const authService = {
  login,
  register,
};

export default authService;
