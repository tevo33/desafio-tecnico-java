import React, { useState, useEffect } from 'react';
import { 
  Container, Typography, Paper, Table, TableBody, TableCell, TableContainer, 
  TableHead, TableRow, Button, IconButton, Dialog, DialogTitle, DialogContent, 
  DialogActions, TextField, Stack, CircularProgress, Alert
} from '@mui/material';
import { RestauranteService } from '../services/RestauranteService';
import { Restaurante } from '../types';

const Restaurantes: React.FC = () => {
  const [restaurantes, setRestaurantes] = useState<Restaurante[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [editItem, setEditItem] = useState<Restaurante | null>(null);
  const [formData, setFormData] = useState<Omit<Restaurante, 'id'>>({
    nome: '',
    tipo: '',
    endereco: ''
  });

  const carregarRestaurantes = async () => {
    setLoading(true);
    try {
      const data = await RestauranteService.getAll();
      setRestaurantes(data);
      setError(null);
    } catch (err) {
      setError('Erro ao carregar a lista de restaurantes.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarRestaurantes();
  }, []);

  const handleOpenDialog = (item: Restaurante | null = null) => {
    if (item) {
      setEditItem(item);
      setFormData({ 
        nome: item.nome, 
        tipo: item.tipo, 
        endereco: item.endereco 
      });
    } else {
      setEditItem(null);
      setFormData({ nome: '', tipo: '', endereco: '' });
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async () => {
    try {
      if (editItem) {
        await RestauranteService.update(editItem.id, { ...formData, id: editItem.id });
      } else {
        await RestauranteService.create(formData as Restaurante);
      }
      handleCloseDialog();
      carregarRestaurantes();
    } catch (err) {
      console.error('Erro ao salvar:', err);
      setError('Erro ao salvar os dados.');
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir este restaurante?')) {
      try {
        await RestauranteService.delete(id);
        carregarRestaurantes();
      } catch (err) {
        console.error('Erro ao excluir:', err);
        setError('Erro ao excluir o restaurante.');
      }
    }
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Stack direction="row" justifyContent="space-between" alignItems="center" mb={3}>
          <Typography variant="h4">Restaurantes</Typography>
          <Button 
            variant="contained" 
            color="primary" 
            onClick={() => handleOpenDialog()}
          >
            Novo Restaurante
          </Button>
        </Stack>

        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        {loading ? (
          <CircularProgress sx={{ display: 'block', m: 'auto', my: 4 }} />
        ) : (
          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Nome</TableCell>
                  <TableCell>Tipo</TableCell>
                  <TableCell>Endereço</TableCell>
                  <TableCell align="right">Ações</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {restaurantes.length > 0 ? (
                  restaurantes.map((restaurante) => (
                    <TableRow key={restaurante.id}>
                      <TableCell>{restaurante.nome}</TableCell>
                      <TableCell>{restaurante.tipo}</TableCell>
                      <TableCell>{restaurante.endereco}</TableCell>
                      <TableCell align="right">
                        <IconButton 
                          color="primary" 
                          onClick={() => handleOpenDialog(restaurante)}
                          aria-label="editar"
                        >
                          Editar
                        </IconButton>
                        <IconButton 
                          color="error" 
                          onClick={() => handleDelete(restaurante.id)}
                          aria-label="excluir"
                        >
                          Excluir
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  ))
                ) : (
                  <TableRow>
                    <TableCell colSpan={4} align="center">
                      Nenhum restaurante cadastrado.
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </TableContainer>
        )}
      </Paper>

      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>
          {editItem ? 'Editar Restaurante' : 'Novo Restaurante'}
        </DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            name="nome"
            label="Nome"
            type="text"
            fullWidth
            variant="outlined"
            value={formData.nome}
            onChange={handleChange}
            sx={{ mb: 2, mt: 1 }}
          />
          <TextField
            margin="dense"
            name="tipo"
            label="Tipo"
            type="text"
            fullWidth
            variant="outlined"
            value={formData.tipo}
            onChange={handleChange}
            sx={{ mb: 2 }}
          />
          <TextField
            margin="dense"
            name="endereco"
            label="Endereço"
            type="text"
            fullWidth
            variant="outlined"
            value={formData.endereco}
            onChange={handleChange}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Cancelar</Button>
          <Button onClick={handleSubmit} variant="contained" color="primary">
            Salvar
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default Restaurantes; 