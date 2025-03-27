import api from './api';
import { Voto, ResultadoVotacao } from '../types';

export const VotacaoService = {
  votar: async (voto: Voto): Promise<Voto> => {
    const response = await api.post('/votacao/votar', voto);
    return response.data;
  },
  
  getResultado: async (): Promise<ResultadoVotacao> => {
    const response = await api.get('/votacao/resultado');
    return response.data;
  },
  
  getVotosHoje: async (): Promise<Voto[]> => {
    const response = await api.get('/votacao/votos/hoje');
    return response.data;
  }
}; 