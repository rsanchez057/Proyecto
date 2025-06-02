import React, { useState } from 'react';
import { TextField, Button, Paper, Typography, Box } from '@mui/material';

const Login = ({ onLogin }) => {
  const [usuario, setUsuario] = useState('');
  const [contrasena, setContrasena] = useState('');
  const [error, setError] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();

    // Validar credenciales para Coordinador y Profesor
    if (usuario === 'Coordinador' && contrasena === '1234') {
      onLogin('coordinador'); // Llamar al callback con el rol de coordinador
    } else if (usuario === 'Profesor' && contrasena === '12345') {
      onLogin('profesor'); // Llamar al callback con el rol de profesor
    } else {
      setError('Usuario o contraseña incorrectos');
    }
  };

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
      bgcolor="background.default"
    >
      <Paper elevation={4} sx={{ p: 5, width: 350 }}>
        <Typography variant="h5" align="center" gutterBottom>
          Iniciar Sesión
        </Typography>
        <form onSubmit={handleLogin}>
          <TextField
            label="Usuario"
            variant="outlined"
            fullWidth
            sx={{ mb: 3 }}
            value={usuario}
            onChange={(e) => setUsuario(e.target.value)}
          />
          <TextField
            label="Contraseña"
            type="password"
            variant="outlined"
            fullWidth
            sx={{ mb: 3 }}
            value={contrasena}
            onChange={(e) => setContrasena(e.target.value)}
          />
          <Button
            variant="contained"
            color="primary"
            fullWidth
            size="large"
            type="submit"
          >
            Entrar
          </Button>
        </form>
        {error && <Typography color="error" align="center" sx={{ mt: 2 }}>{error}</Typography>}
      </Paper>
    </Box>
  );
};

export default Login;
