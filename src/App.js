import React, { useState } from 'react';
import './App.css';
import Appbar from './components/Appbar';
import AdminDashboard from './components/AdminDashboard';
import Login from './components/Login';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

function App() {
  const [usuarioLogueado, setUsuarioLogueado] = useState(false);
  const [modoOscuro, setModoOscuro] = useState(false);

  const theme = createTheme({
    palette: {
      mode: modoOscuro ? 'dark' : 'light',
      primary: {
        main: '#1976d2',
      },
      secondary: {
        main: '#d32f2f',
      },
    },
  });

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div className="App">
        <Appbar modoOscuro={modoOscuro} setModoOscuro={setModoOscuro} />
        {usuarioLogueado ? (
          <AdminDashboard />
        ) : (
          <Login onLogin={() => setUsuarioLogueado(true)} />
        )}
      </div>
    </ThemeProvider>
  );
}

export default App;
