import api from './api';
import { Profissional } from '../types';

export const ProfissionalService = {
  getAll: async (): Promise<Profissional[]> => {
    const response = await api.get('/profissionais');
    return response.data;
  },
  
  getById: async (id: number): Promise<Profissional> => {
    const response = await api.get(`/profissionais/${id}`);
    return response.data;
  },
  
  create: async (profissional: Profissional): Promise<Profissional> => {
    const response = await api.post('/profissionais', profissional);
    return response.data;
  },
  
  update: async (id: number, profissional: Profissional): Promise<Profissional> => {
    const response = await api.put(`/profissionais/${id}`, profissional);
    return response.data;
  },
  
  delete: async (id: number): Promise<void> => {
    await api.delete(`/profissionais/${id}`);
  }
}; 