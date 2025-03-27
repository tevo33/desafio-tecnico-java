import React, { useState, useEffect } from 'react';
import { Container, Typography, Paper, FormControl, InputLabel, Select, MenuItem, Button, Alert, Box } from '@mui/material';
import { ProfissionalService } from '../services/ProfissionalService';
import { RestauranteService } from '../services/RestauranteService';
import { VotacaoService } from '../services/VotacaoService';
import { Profissional, Restaurante } from '../types';

const Votar: React.FC = () => {
  const [profissionais, setProfissionais] = useState<Profissional[]>([]);
  const [restaurantes, setRestaurantes] = useState<Restaurante[]>([]);
  const [profissionalSelecionado, setProfissionalSelecionado] = useState<number | ''>('');
  const [restauranteSelecionado, setRestauranteSelecionado] = useState<number | ''>('');
  const [mensagem, setMensagem] = useState<{ tipo: 'success' | 'error', texto: string } | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const carregarDados = async () => {
      try {
        const [profissionaisData, restaurantesData] = await Promise.all([
          ProfissionalService.getAll(),
          RestauranteService.getDisponiveis()
        ]);
        
        setProfissionais(profissionaisData);
        setRestaurantes(restaurantesData);
      } catch (error) {
        setMensagem({ tipo: 'error', texto: 'Erro ao carregar dados. Tente novamente.' });
      }
    };
    
    carregarDados();
  }, []);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    
    if (profissionalSelecionado === '' || restauranteSelecionado === '') {
      setMensagem({ tipo: 'error', texto: 'Selecione um profissional e um restaurante.' });
      return;
    }
    
    setLoading(true);
    
    try {
      const profissional = profissionais.find(p => p.id === profissionalSelecionado);
      const restaurante = restaurantes.find(r => r.id === restauranteSelecionado);
      
      if (!profissional || !restaurante) {
        throw new Error('Profissional ou restaurante não encontrado');
      }
      
      await VotacaoService.votar({
        profissional,
        restaurante
      });
      
      setMensagem({ tipo: 'success', texto: 'Voto registrado com sucesso!' });
      setProfissionalSelecionado('');
      setRestauranteSelecionado('');
    } catch (error: any) {
      setMensagem({ 
        tipo: 'error', 
        texto: error.response?.data || 'Erro ao registrar voto. Tente novamente.' 
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" gutterBottom>
          Votar no Restaurante
        </Typography>

        {mensagem && (
          <Alert severity={mensagem.tipo} sx={{ mb: 2 }}>
            {mensagem.texto}
          </Alert>
        )}

        <form onSubmit={handleSubmit}>
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <FormControl fullWidth>
              <InputLabel id="profissional-label">Profissional</InputLabel>
              <Select
                labelId="profissional-label"
                value={profissionalSelecionado}
                label="Profissional"
                onChange={(e) => setProfissionalSelecionado(e.target.value as number)}
              >
                {profissionais.map((profissional) => (
                  <MenuItem key={profissional.id} value={profissional.id}>
                    {profissional.nome}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            <FormControl fullWidth>
              <InputLabel id="restaurante-label">Restaurante</InputLabel>
              <Select
                labelId="restaurante-label"
                value={restauranteSelecionado}
                label="Restaurante"
                onChange={(e) => setRestauranteSelecionado(e.target.value as number)}
              >
                {restaurantes.map((restaurante) => (
                  <MenuItem key={restaurante.id} value={restaurante.id}>
                    {restaurante.nome} - {restaurante.tipo}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            <Button 
              type="submit" 
              variant="contained" 
              color="primary"
              disabled={loading || profissionalSelecionado === '' || restauranteSelecionado === ''}
            >
              {loading ? 'Enviando...' : 'Votar'}
            </Button>
          </Box>
        </form>
      </Paper>
    </Container>
  );
};

export default Votar; 