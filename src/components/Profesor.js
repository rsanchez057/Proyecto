import React, { useState, useEffect } from 'react';
import { Container, Paper, TextField, Button, Grid } from '@mui/material';

const Profesor = () => {
    const [form, setForm] = useState({
        cif: '',
        nombre: '',
        apellidos: '',
        email: '',
        facultad: { id: '' }
    });
    const [profesores, setProfesores] = useState([]);
    const [facultades, setFacultades] = useState([]);
    const [editMode, setEditMode] = useState(false);

    useEffect(() => {
        fetch("http://localhost:8181/api/profesor/all")
            .then(res => res.json())
            .then(data => setProfesores(data));

        fetch("http://localhost:8181/api/facultad/all")
            .then(res => res.json())
            .then(data => setFacultades(data));
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'facultad') {
            setForm({ ...form, facultad: { id: value } });
        } else {
            setForm({ ...form, [name]: value });
        }
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
            setForm({ cif: '', nombre: '', apellidos: '', email: '', facultad: { id: '' } });
            setEditMode(false);
            fetch("http://localhost:8181/api/profesor/all")
                .then(res => res.json())
                .then(data => setProfesores(data));
        });
    };

    const handleEdit = (p) => {
        setForm({
            cif: p.cif,
            nombre: p.nombres,
            apellidos: p.apellidos,
            email: p.email,
            facultad: { id: p.facultad?.id || '' }
        });
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
                    <TextField label="Nombres" name="nombre" value={form.nombres} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
                    <TextField label="Apellidos" name="apellidos" value={form.apellidos} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
                    <TextField label="Email" name="email" value={form.email} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
                    <TextField
                        select
                        label="Facultad"
                        name="facultad"
                        value={form.facultad.id}
                        onChange={handleChange}
                        fullWidth
                        sx={{ mb: 2 }}
                        SelectProps={{ native: true }}
                        InputLabelProps={{ shrink: true }}
                    >
                        <option value="">Seleccione una facultad</option>
                        {facultades.map(f => (
                            <option key={f.id} value={f.id}>{f.nombre}</option>
                        ))}
                    </TextField>
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
                            <p><strong>Facultad:</strong> {p.facultad?.nombre || 'Sin asignar'}</p>
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
