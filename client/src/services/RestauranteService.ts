import api from './api';
import { Restaurante } from '../types';

export const RestauranteService = {
  getAll: async (): Promise<Restaurante[]> => {
    const response = await api.get('/restaurantes');
    return response.data;
  },
  
  getDisponiveis: async (): Promise<Restaurante[]> => {
    const response = await api.get('/restaurantes/disponiveis');
    return response.data;
  },
  
  getById: async (id: number): Promise<Restaurante> => {
    const response = await api.get(`/restaurantes/${id}`);
    return response.data;
  },
  
  create: async (restaurante: Restaurante): Promise<Restaurante> => {
    const response = await api.post('/restaurantes', restaurante);
    return response.data;
  },
  
  update: async (id: number, restaurante: Restaurante): Promise<Restaurante> => {
    const response = await api.put(`/restaurantes/${id}`, restaurante);
    return response.data;
  },
  
  delete: async (id: number): Promise<void> => {
    await api.delete(`/restaurantes/${id}`);
  }
}; 