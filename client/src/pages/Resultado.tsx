import React, { useState, useEffect } from 'react';
import { Container, Typography, Paper, Box, Alert, CircularProgress, Card, CardContent, Divider } from '@mui/material';
import { VotacaoService } from '../services/VotacaoService';
import { ResultadoVotacao } from '../types';

const Resultado: React.FC = () => {
  const [resultado, setResultado] = useState<ResultadoVotacao | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const carregarResultado = async () => {
      try {
        const data = await VotacaoService.getResultado();
        setResultado(data);
      } catch (err) {
        setError('Ainda não há resultado para hoje. Os resultados são divulgados às 12h.');
      } finally {
        setLoading(false);
      }
    };

    carregarResultado();

    // Atualizar a cada 1 minuto
    const interval = setInterval(carregarResultado, 60000);
    return () => clearInterval(interval);
  }, []);

  const formatarData = (dataString: string) => {
    const data = new Date(dataString);
    return data.toLocaleDateString('pt-BR');
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" gutterBottom>
          Resultado da Votação
        </Typography>

        {loading ? (
          <Box sx={{ display: 'flex', justifyContent: 'center', my: 4 }}>
            <CircularProgress />
          </Box>
        ) : error ? (
          <Alert severity="info" sx={{ my: 2 }}>
            {error}
          </Alert>
        ) : resultado ? (
          <Box sx={{ mt: 3 }}>
            <Card variant="outlined">
              <CardContent>
                <Typography variant="h5" gutterBottom>
                  {resultado.restaurante.nome}
                </Typography>
                <Divider sx={{ my: 1 }} />
                <Typography variant="body1" color="text.secondary">
                  Tipo: {resultado.restaurante.tipo}
                </Typography>
                <Typography variant="body1" color="text.secondary">
                  Endereço: {resultado.restaurante.endereco}
                </Typography>
                <Typography variant="body1" color="text.secondary" sx={{ mt: 1 }}>
                  Data: {formatarData(resultado.data)}
                </Typography>
                <Box sx={{ mt: 2, display: 'flex', alignItems: 'center' }}>
                  <Typography variant="h6" color="primary">
                    Total de votos: {resultado.quantidadeVotos}
                  </Typography>
                </Box>
              </CardContent>
            </Card>
          </Box>
        ) : (
          <Alert severity="info" sx={{ my: 2 }}>
            Nenhum resultado disponível. Verifique novamente às 12h.
          </Alert>
        )}
      </Paper>
    </Container>
  );
};

export default Resultado; 