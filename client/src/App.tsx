import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { CssBaseline, ThemeProvider, createTheme } from '@mui/material';
import Header from './components/Header';
import Home from './pages/Home';
import Votar from './pages/Votar';
import Resultado from './pages/Resultado';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <Header />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/votar" element={<Votar />} />
          <Route path="/resultado" element={<Resultado />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
