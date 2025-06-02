import React, { useState, useEffect } from 'react';
import {
    Container, Paper, TextField, Button,
    Grid, MenuItem, Typography
} from '@mui/material';

const Alumno = () => {
    const [alumnos, setAlumnos] = useState([]);
    const [facultades, setFacultades] = useState([]);
    const [form, setForm] = useState({
        cif: '',
        nombre: '',
        apellidos: '',
        email: '',
        facultad: '',
    });
    const [editMode, setEditMode] = useState(false);
    const [currentCif, setCurrentCif] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8181/api/alumno/all')
            .then(res => res.json())
            .then(setAlumnos);

        fetch('http://localhost:8181/api/facultad/all')
            .then(res => res.json())
            .then(setFacultades);
    }, []);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        fetch('http://localhost:8181/api/alumno/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(form),
        }).then(() => {
            setForm({ cif: '', nombre: '', apellidos: '', email: '', facultad: '' });
            fetch('http://localhost:8181/api/alumno/all')
                .then(res => res.json())
                .then(setAlumnos);
        });
    };

    const handleEdit = (alumno) => {
        setForm(alumno);
        setCurrentCif(alumno.cif);
        setEditMode(true);
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
    
        try {
            const response = await fetch(`http://localhost:8181/api/alumno/update/${currentCif}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(form),
            });
    
            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Error al actualizar el alumno: ${errorMessage}`);
            }
    
            // Resetear el formulario y el estado
            setForm({ cif: '', nombre: '', apellidos: '', email: '', facultad: '' });
            setEditMode(false);
            setCurrentCif(null);
    
            // Recargar la lista de alumnos
            const alumnosResponse = await fetch('http://localhost:8181/api/alumno/all');
            if (!alumnosResponse.ok) {
                throw new Error('Error al cargar la lista de alumnos después de la actualización');
            }
            const alumnos = await alumnosResponse.json();
            setAlumnos(alumnos);
    
        } catch (error) {
            console.error('Error en la actualización del alumno:', error);
            alert(`Error: ${error.message}`);
        }
    };

    const handleDelete = (cif) => {
        fetch(`http://localhost:8181/api/alumno/delete/${cif}`, {
            method: 'DELETE',
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Error al eliminar el alumno');
                }
                // Actualizar el estado local eliminando el alumno de la lista
                setAlumnos((prevAlumnos) => prevAlumnos.filter((alumno) => alumno.cif !== cif));
            })
            .catch((error) => {
                console.error('Error al eliminar el alumno:', error);
            });
    };

    return (
        <Container>
            <Grid container spacing={3}>
                {/* Formulario a la izquierda */}
                <Grid item xs={12} md={4}>
                    <Paper elevation={3} style={{ padding: '30px' }}>
                        <Typography variant="h5" color="primary" gutterBottom>
                            {editMode ? 'Editar Alumno' : 'Agregar Alumno'}
                        </Typography>
                        <form onSubmit={editMode ? handleUpdate : handleSubmit}>
                            <TextField
                                name="cif"
                                label="CIF"
                                variant="outlined"
                                fullWidth
                                value={form.cif}
                                onChange={handleChange}
                                margin="normal"
                                required
                                disabled={editMode}
                            />
                            <TextField
                                name="nombre"
                                label="Nombres"
                                variant="outlined"
                                fullWidth
                                value={form.nombres}
                                onChange={handleChange}
                                margin="normal"
                                required
                            />
                            <TextField
                                name="apellidos"
                                label="Apellido"
                                variant="outlined"
                                fullWidth
                                value={form.apellido}
                                onChange={handleChange}
                                margin="normal"
                                required
                            />
                            <TextField
                                name="email"
                                label="Email"
                                variant="outlined"
                                fullWidth
                                value={form.email}
                                onChange={handleChange}
                                margin="normal"
                            />
                            <TextField
                                name="facultad"
                                label="Facultad"
                                select
                                fullWidth
                                onChange={handleChange}
                                margin="normal"
                                value={form.facultad.id}
                                sx={{ mb: 2 }}
                                required
                                InputLabelProps={{ shrink: true }}
                            >
                                {facultades.map((f) => (
                                    <MenuItem key={f.codigo} value={f.nombre}>
                                        {f.nombre}
                                    </MenuItem>
                                ))}
                            </TextField>
                            <Button
                                variant="contained"
                                color="primary"
                                type="submit"
                                fullWidth
                                sx={{ mt: 2 }}
                            >
                                {editMode ? 'Actualizar' : 'Agregar'}
                            </Button>
                        </form>
                    </Paper>
                </Grid>

                {/* Lista de alumnos a la derecha */}
                <Grid item xs={12} md={8}>
                    <Paper elevation={3} style={{ padding: '20px', height: '600px', overflowY: 'auto' }}>
                        <Typography variant="h5" gutterBottom>
                            Lista de Alumnos
                        </Typography>
                        <Grid container spacing={2}>
                            {alumnos.map((alumno) => (
                                <Grid item xs={12} key={alumno.cif}>
                                    <Paper elevation={2} style={{ padding: '10px' }}>
                                        <Typography variant="h6">{alumno.nombres} {alumno.apellido}</Typography>
                                        <Typography variant="body2"><strong>CIF:</strong> {alumno.cif}</Typography>
                                        <Typography variant="body2"><strong>Email:</strong> {alumno.email}</Typography>
                                        <Typography variant="body2"><strong>Facultad:</strong> {alumno.facultad}</Typography>
                                        <Button
                                            variant="contained"
                                            color="secondary"
                                            onClick={() => handleEdit(alumno)}
                                            sx={{ mt: 1, mr: 1 }}
                                        >
                                            Editar
                                        </Button>
                                        <Button
                                            variant="contained"
                                            color="error"
                                            onClick={() => handleDelete(alumno.cif)}
                                            sx={{ mt: 1 }}
                                        >
                                            Eliminar
                                        </Button>
                                    </Paper>
                                </Grid>
                            ))}
                        </Grid>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Alumno;
