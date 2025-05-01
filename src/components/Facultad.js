import React, { useState, useEffect } from 'react';
import { Container, Paper, TextField, Button, Grid } from '@mui/material';

const Facultad = () => {
    const [nombre, setNombre] = useState('');
    const [facultades, setFacultades] = useState([]);
    const [editId, setEditId] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8181/api/facultad/all")
            .then(res => res.json())
            .then(data => setFacultades(data));
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        const facultad = { nombre };

        const url = editId
            ? "http://localhost:8181/api/facultad/update"
            : "http://localhost:8181/api/facultad/save";
        const method = editId ? "PUT" : "POST";

        fetch(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(editId ? { id: editId, ...facultad } : facultad),
        }).then(() => {
            setNombre('');
            setEditId(null);
            fetch("http://localhost:8181/api/facultad/all")
                .then(res => res.json())
                .then(data => setFacultades(data));
        });
    };

    const handleEdit = (f) => {
        setNombre(f.nombre);
        setEditId(f.id);
    };

    const handleDelete = (id) => {
        fetch(`http://localhost:8181/api/facultad/delete/${id}`, { method: 'DELETE' })
            .then(() => setFacultades(facultades.filter(f => f.id !== id)));
    };

    return (
        <Container>
            <Paper style={{ padding: '20px', margin: '20px auto', width: 600 }}>
                <h2>{editId ? 'Editar Facultad' : 'Agregar Facultad'}</h2>
                <form onSubmit={handleSubmit}>
                    <TextField
                        label="Nombre de la Facultad"
                        value={nombre}
                        onChange={(e) => setNombre(e.target.value)}
                        fullWidth
                        sx={{ mb: 2 }}
                    />
                    <Button type="submit" variant="contained" color="primary">
                        {editId ? 'Actualizar' : 'Guardar'}
                    </Button>
                </form>
            </Paper>

            <Grid container spacing={2}>
                {facultades.map((f) => (
                    <Grid item xs={12} sm={6} key={f.id}>
                        <Paper style={{ padding: '10px' }}>
                            <h4>{f.nombre}</h4>
                            <Button onClick={() => handleEdit(f)} variant="contained">Editar</Button>
                            <Button onClick={() => handleDelete(f.id)} variant="outlined" color="error" style={{ marginLeft: 10 }}>
                                Eliminar
                            </Button>
                        </Paper>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default Facultad;
