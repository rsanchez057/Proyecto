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
        nombres: '',
        apellido: '',
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
            setForm({ cif: '', nombres: '', apellido: '', email: '', facultad: '' });
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

    const handleUpdate = (e) => {
        e.preventDefault();
        fetch(`http://localhost:8181/api/alumno/update/${currentCif}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(form),
        }).then(() => {
            setForm({ cif: '', nombres: '', apellido: '', email: '', facultad: '' });
            setEditMode(false);
            setCurrentCif(null);
            fetch('http://localhost:8181/api/alumno/all')
                .then(res => res.json())
                .then(setAlumnos);
        });
    };

    const handleDelete = (cif) => {
        fetch(`http://localhost:8181/api/alumno/delete/${cif}`, {
            method: 'DELETE',
        }).then(() => {
            setAlumnos(alumnos.filter(a => a.cif !== cif));
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
                                name="nombres"
                                label="Nombres"
                                variant="outlined"
                                fullWidth
                                value={form.nombres}
                                onChange={handleChange}
                                margin="normal"
                                required
                            />
                            <TextField
                                name="apellido"
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
