import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';

const Header: React.FC = () => {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Sistema de Votação de Restaurantes
        </Typography>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button color="inherit" component={Link} to="/">
            Home
          </Button>
          <Button color="inherit" component={Link} to="/votar">
            Votar
          </Button>
          <Button color="inherit" component={Link} to="/resultado">
            Resultado
          </Button>
          <Button color="inherit" component={Link} to="/restaurantes">
            Restaurantes
          </Button>
          <Button color="inherit" component={Link} to="/profissionais">
            Profissionais
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header; 