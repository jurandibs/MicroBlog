import axios from 'axios';
import type { Post } from '../types';

const API_URL = 'http://localhost:8080/posts';

const getAuthHeaders = () => {
  const token = JSON.parse(localStorage.getItem('token') || 'null');
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

const getAll = async (): Promise<Post[]> => {
  const response = await axios.get(API_URL + '/getAll', getAuthHeaders());
  return response.data;
};

const get = async (id: number): Promise<Post> => {
  const response = await axios.get(`${API_URL}/get?id=${id}`, getAuthHeaders());
  return response.data;
};

const save = async (post: Partial<Post>): Promise<Post> => {
  const response = await axios.post(`${API_URL}/save`, post, getAuthHeaders());
  return response.data;
};

const update = async (id: number, post: Partial<Post>): Promise<Post> => {
  const response = await axios.post(`${API_URL}/update?id=${id}`, post, getAuthHeaders());
  return response.data;
};

const remove = async (id: number): Promise<void> => {
  await axios.delete(`${API_URL}/delete?id=${id}`, getAuthHeaders());
};

const postService = {
  getAll,
  get,
  save,
  update,
  remove,
};

export default postService;
