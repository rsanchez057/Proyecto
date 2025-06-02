import React, { useState } from 'react';
import './App.css';
import Appbar from './components/Appbar';
import AdminDashboard from './components/AdminDashboard';
import Login from './components/Login';
import ProfesorDashboard from './components/ProfesorDashboard'; // Importar el dashboard para el Profesor
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

function App() {
  const [usuarioLogueado, setUsuarioLogueado] = useState(false);
  const [rolUsuario, setRolUsuario] = useState(null); // Para almacenar el rol del usuario
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

  // Función para manejar el login y asignar el rol
  const handleLogin = (rol) => {
    setRolUsuario(rol);  // Asignamos el rol del usuario
    setUsuarioLogueado(true);  // Marcamos que el usuario está logueado
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div className="App">
        <Appbar modoOscuro={modoOscuro} setModoOscuro={setModoOscuro} />
        
        {usuarioLogueado ? (
          // Redirigir a la vista adecuada según el rol
          rolUsuario === 'coordinador' ? (
            <AdminDashboard /> // Si el rol es coordinador, mostramos el AdminDashboard
          ) : rolUsuario === 'profesor' ? (
            <ProfesorDashboard /> // Si el rol es profesor, mostramos el ProfesorDashboard
          ) : null
        ) : (
          <Login onLogin={handleLogin} /> // Si no está logueado, mostramos el Login
        )}
      </div>
    </ThemeProvider>
  );
}

export default App;
