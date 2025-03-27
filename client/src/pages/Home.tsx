import React from 'react';
import { Container, Typography, Paper, Box } from '@mui/material';

const Home: React.FC = () => {
  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" gutterBottom>
          Bem-vindo ao Sistema de Votação de Restaurantes
        </Typography>
        <Typography variant="body1" paragraph>
          Este sistema ajuda os times da DBServer a escolher democraticamente onde almoçar todos os dias.
        </Typography>
        <Box sx={{ mt: 2 }}>
          <Typography variant="h6" gutterBottom>
            Como funciona:
          </Typography>
          <Typography variant="body1" component="div">
            <ul>
              <li>Cada profissional pode votar em um restaurante por dia</li>
              <li>O mesmo restaurante não pode ser escolhido mais de uma vez durante a semana</li>
              <li>O resultado da votação é exibido antes do meio-dia</li>
            </ul>
          </Typography>
        </Box>
      </Paper>
    </Container>
  );
};

export default Home; 