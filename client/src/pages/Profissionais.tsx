import React, { useState, useEffect } from 'react';
import { 
  Container, Typography, Paper, Table, TableBody, TableCell, TableContainer, 
  TableHead, TableRow, Button, IconButton, Dialog, DialogTitle, DialogContent, 
  DialogActions, TextField, Stack, CircularProgress, Alert
} from '@mui/material';
import { ProfissionalService } from '../services/ProfissionalService';
import { Profissional } from '../types';

const Profissionais: React.FC = () => {
  const [profissionais, setProfissionais] = useState<Profissional[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [editItem, setEditItem] = useState<Profissional | null>(null);
  const [formData, setFormData] = useState<Omit<Profissional, 'id'>>({
    nome: '',
    email: ''
  });

  const carregarProfissionais = async () => {
    setLoading(true);
    try {
      const data = await ProfissionalService.getAll();
      setProfissionais(data);
      setError(null);
    } catch (err) {
      setError('Erro ao carregar a lista de profissionais.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarProfissionais();
  }, []);

  const handleOpenDialog = (item: Profissional | null = null) => {
    if (item) {
      setEditItem(item);
      setFormData({ nome: item.nome, email: item.email });
    } else {
      setEditItem(null);
      setFormData({ nome: '', email: '' });
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
        await ProfissionalService.update(editItem.id, { ...formData, id: editItem.id });
      } else {
        await ProfissionalService.create(formData as Profissional);
      }
      handleCloseDialog();
      carregarProfissionais();
    } catch (err) {
      console.error('Erro ao salvar:', err);
      setError('Erro ao salvar os dados.');
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir este profissional?')) {
      try {
        await ProfissionalService.delete(id);
        carregarProfissionais();
      } catch (err) {
        console.error('Erro ao excluir:', err);
        setError('Erro ao excluir o profissional.');
      }
    }
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Stack direction="row" justifyContent="space-between" alignItems="center" mb={3}>
          <Typography variant="h4">Profissionais</Typography>
          <Button 
            variant="contained" 
            color="primary" 
            onClick={() => handleOpenDialog()}
          >
            Novo Profissional
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
                  <TableCell>Email</TableCell>
                  <TableCell align="right">Ações</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {profissionais.length > 0 ? (
                  profissionais.map((profissional) => (
                    <TableRow key={profissional.id}>
                      <TableCell>{profissional.nome}</TableCell>
                      <TableCell>{profissional.email}</TableCell>
                      <TableCell align="right">
                        <IconButton 
                          color="primary" 
                          onClick={() => handleOpenDialog(profissional)}
                          aria-label="editar"
                        >
                          Editar
                        </IconButton>
                        <IconButton 
                          color="error" 
                          onClick={() => handleDelete(profissional.id)}
                          aria-label="excluir"
                        >
                          Excluir
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  ))
                ) : (
                  <TableRow>
                    <TableCell colSpan={3} align="center">
                      Nenhum profissional cadastrado.
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
          {editItem ? 'Editar Profissional' : 'Novo Profissional'}
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
            name="email"
            label="Email"
            type="email"
            fullWidth
            variant="outlined"
            value={formData.email}
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

export default Profissionais; 