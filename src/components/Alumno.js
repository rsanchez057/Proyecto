import React, { useState, useEffect } from 'react';
import { Container, Paper, TextField, Button, Grid } from '@mui/material';

const Alumno = () => {
    const paperStyle = { padding: '50px 20px', width: 600, margin: '20px auto' };
    const [alumnos, setAlumnos] = useState([]);
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
            .then((res) => res.json())
            .then((result) => setAlumnos(result));
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
                .then((res) => res.json())
                .then((result) => setAlumnos(result));
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
                .then((res) => res.json())
                .then((result) => setAlumnos(result));
        });
    };

    const handleDelete = (cif) => {
        fetch(`http://localhost:8181/api/alumno/delete/${cif}`, {
            method: 'DELETE',
        }).then(() => {
            setAlumnos(alumnos.filter((s) => s.cif !== cif));
        });
    };

    return (
        <Container>
            <Paper elevation={3} style={paperStyle}>
                <h1 style={{ color: 'blue' }}>
                    <u>{editMode ? 'Editar Alumno' : 'Agregar Alumno'}</u>
                </h1>
                <form noValidate autoComplete="off" onSubmit={editMode ? handleUpdate : handleSubmit}>
                    <TextField
                        name="cif"
                        label="CIF"
                        variant="outlined"
                        fullWidth
                        value={form.cif}
                        onChange={handleChange}
                        sx={{ mb: 2 }}
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
                        sx={{ mb: 2 }}
                        required
                    />
                    <TextField
                        name="apellido"
                        label="Apellido"
                        variant="outlined"
                        fullWidth
                        value={form.apellido}
                        onChange={handleChange}
                        sx={{ mb: 2 }}
                        required
                    />
                    <TextField
                        name="email"
                        label="Email"
                        variant="outlined"
                        fullWidth
                        value={form.email}
                        onChange={handleChange}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        name="facultad"
                        label="Facultad"
                        variant="outlined"
                        fullWidth
                        value={form.facultad}
                        onChange={handleChange}
                        sx={{ mb: 2 }}
                    />
                    <Button variant="contained" color="primary" type="submit">
                        {editMode ? 'Actualizar' : 'Agregar'}
                    </Button>
                </form>
            </Paper>

            <Grid container spacing={2} style={{ marginTop: '20px' }}>
                {alumnos.map((alumno) => (
                    <Grid item xs={12} sm={6} md={4} key={alumno.cif}>
                        <Paper elevation={3} style={{ padding: '10px', margin: '10px' }}>
                            <h3>{alumno.nombres} {alumno.apellido}</h3>
                            <p><strong>CIF:</strong> {alumno.cif}</p>
                            <p><strong>Email:</strong> {alumno.email}</p>
                            <p><strong>Facultad:</strong> {alumno.facultad}</p>
                            <Button
                                variant="contained"
                                color="secondary"
                                onClick={() => handleEdit(alumno)}
                                style={{ marginRight: '10px' }}
                            >
                                Editar
                            </Button>
                            <Button
                                variant="contained"
                                color="error"
                                onClick={() => handleDelete(alumno.cif)}
                            >
                                Eliminar
                            </Button>
                        </Paper>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default Alumno;
