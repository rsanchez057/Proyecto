import React, { useState, useEffect } from 'react';
import { Container, Paper, TextField, Button, Grid } from '@mui/material';

const Profesor = () => {
    const [form, setForm] = useState({ cif: '', nombres: '', apellidos: '', email: '' });
    const [profesores, setProfesores] = useState([]);
    const [editMode, setEditMode] = useState(false);

    useEffect(() => {
        fetch("http://localhost:8181/api/profesor/all")
            .then(res => res.json())
            .then(data => setProfesores(data));
    }, []);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const url = editMode
            ? "http://localhost:8181/api/profesor/update"
            : "http://localhost:8181/api/profesor/save";
        const method = editMode ? "PUT" : "POST";

        fetch(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(form),
        }).then(() => {
            setForm({ cif: '', nombres: '', apellidos: '', email: '' });
            setEditMode(false);
            fetch("http://localhost:8181/api/profesor/all")
                .then(res => res.json())
                .then(data => setProfesores(data));
        });
    };

    const handleEdit = (p) => {
        setForm(p);
        setEditMode(true);
    };

    const handleDelete = (cif) => {
        fetch(`http://localhost:8181/api/profesor/delete/${cif}`, { method: 'DELETE' })
            .then(() => setProfesores(profesores.filter(p => p.cif !== cif)));
    };

    return (
        <Container>
            <Paper style={{ padding: '20px', margin: '20px auto', width: 600 }}>
                <h2>{editMode ? 'Editar Profesor' : 'Agregar Profesor'}</h2>
                <form onSubmit={handleSubmit}>
                    <TextField label="CIF" name="cif" value={form.cif} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
                    <TextField label="Nombres" name="nombres" value={form.nombres} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
                    <TextField label="Apellidos" name="apellidos" value={form.apellidos} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
                    <TextField label="Email" name="email" value={form.email} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
                    <Button type="submit" variant="contained" color="primary">
                        {editMode ? 'Actualizar' : 'Guardar'}
                    </Button>
                </form>
            </Paper>

            <Grid container spacing={2}>
                {profesores.map((p) => (
                    <Grid item xs={12} sm={6} key={p.cif}>
                        <Paper style={{ padding: '10px' }}>
                            <h4>{p.nombres} {p.apellidos}</h4>
                            <p>{p.email}</p>
                            <p><strong>CIF:</strong> {p.cif}</p>
                            <Button onClick={() => handleEdit(p)} variant="contained">Editar</Button>
                            <Button onClick={() => handleDelete(p.cif)} variant="outlined" color="error" style={{ marginLeft: 10 }}>
                                Eliminar
                            </Button>
                        </Paper>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default Profesor;
