import React, { useState } from 'react';
import axios from 'axios';
import { Box, TextField, Button, Typography, Paper } from '@mui/material';

const ProfesorDashboard = () => {
const [titulo, setTitulo] = useState('');
const [descripcion, setDescripcion] = useState('');
const [estado, setEstado] = useState('Pendiente');
const [tipo, setTipo] = useState('');
const [cifAlumno, setCifAlumno] = useState('');
const [cifProfesor, setCifProfesor] = useState('');
const [fecha, setFecha] = useState('');
const [error, setError] = useState('');
const [success, setSuccess] = useState(false);

const handleSubmit = (e) => {
    e.preventDefault();

    const incidenciaData = {
      titulo,
      descripcion,
      fecha,
      estado,
      tipo,
      cifAlumno,
      cifProfesor,
    };

    axios.post('http://localhost:8181/api/incidencia/save', incidenciaData)
      .then(response => {
        setSuccess(true);
        setError('');
        // Limpiar el formulario
        setTitulo('');
        setDescripcion('');
        setEstado('Pendiente');
        setTipo('');
        setCifAlumno('');
        setCifProfesor('');
        setFecha('');
      })
      .catch(error => {
        setError('Error al crear la incidencia');
        setSuccess(false);
      });
  };

  const handleLogout = () => {
    window.location.reload(); // Reinicia la aplicación y vuelve al login
  };

  return (
    <Box display="flex" flexDirection="column" alignItems="center" minHeight="100vh" bgcolor="background.default" p={4}>
      <Button variant="outlined" color="secondary" onClick={handleLogout} sx={{ alignSelf: 'flex-end', mb: 2 }}>
        Cerrar sesión
      </Button>

      <Paper elevation={4} sx={{ p: 5, width: 400 }}>
        <Typography variant="h5" align="center" gutterBottom>
          Crear Incidencia
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Título"
            variant="outlined"
            fullWidth
            sx={{ mb: 2 }}
            value={titulo}
            onChange={(e) => setTitulo(e.target.value)}
            required
          />
          <TextField
            label="Descripción"
            variant="outlined"
            fullWidth
            sx={{ mb: 2 }}
            value={descripcion}
            onChange={(e) => setDescripcion(e.target.value)}
            required
            multiline
            rows={4}
          />
          <TextField
            label="Tipo"
            variant="outlined"
            fullWidth
            sx={{ mb: 2 }}
            value={tipo}
            onChange={(e) => setTipo(e.target.value)}
            required
          />
          <TextField
            label="CIF Alumno"
            variant="outlined"
            fullWidth
            sx={{ mb: 2 }}
            value={cifAlumno}
            onChange={(e) => setCifAlumno(e.target.value)}
            required
          />
          <TextField
            label="CIF Profesor"
            variant="outlined"
            fullWidth
            sx={{ mb: 2 }}
            value={cifProfesor}
            onChange={(e) => setCifProfesor(e.target.value)}
            required
          />
          <TextField
            label="Fecha (YYYY-MM-DD)"
            variant="outlined"
            fullWidth
            sx={{ mb: 2 }}
            value={fecha}
            onChange={(e) => setFecha(e.target.value)}
            required
          />
          <TextField
            label="Estado"
            variant="outlined"
            fullWidth
            sx={{ mb: 3 }}
            value={estado}
            onChange={(e) => setEstado(e.target.value)}
            required
          />
          <Button
            variant="contained"
            color="primary"
            fullWidth
            size="large"
            type="submit"
          >
            Crear Incidencia
          </Button>
        </form>
        {success && <Typography color="success.main" align="center" sx={{ mt: 2 }}>Incidencia creada con éxito</Typography>}
        {error && <Typography color="error" align="center" sx={{ mt: 2 }}>{error}</Typography>}
      </Paper>
    </Box>
  );
};

export default ProfesorDashboard;


