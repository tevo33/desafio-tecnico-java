export interface Profissional {
  id: number;
  nome: string;
  email: string;
}

export interface Restaurante {
  id: number;
  nome: string;
  endereco: string;
  tipo: string;
}

export interface Voto {
  id?: number;
  profissional: Profissional;
  restaurante: Restaurante;
  data?: string;
}

export interface ResultadoVotacao {
  id: number;
  restaurante: Restaurante;
  data: string;
  quantidadeVotos: number;
} 