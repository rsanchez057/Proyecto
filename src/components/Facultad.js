import React, { useState, useEffect } from 'react';
import { Container, Paper, TextField, Button, Grid } from '@mui/material';

const Facultad = () => {
    const [nombre, setNombre] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [facultades, setFacultades] = useState([]);
    const [editId, setEditId] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8181/api/facultad/all")
            .then(res => res.json())
            .then(data => setFacultades(data));
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!nombre || !descripcion) {
            alert('Por favor, complete todos los campos.');
            return;
        }

        const facultad = { nombre, descripcion };
        const url = editId
            ? "http://localhost:8181/api/facultad/update"
            : "http://localhost:8181/api/facultad/save";
        const method = editId ? "PUT" : "POST";

        fetch(url, {
            method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(editId ? { id: editId, ...facultad } : facultad),
        })
        .then(response => {
            if (response.ok) {
                setNombre('');
                setDescripcion('');
                setEditId(null);
                fetch("http://localhost:8181/api/facultad/all")
                    .then(res => res.json())
                    .then(data => setFacultades(data));
            } else {
                response.text().then(text => {
                    alert('Error: ' + text);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un error de red.');
        });
    };

    const handleEdit = (f) => {
        setNombre(f.nombre);
        setDescripcion(f.descripcion || '');
        setEditId(f.id);
    };

    const handleDelete = (id) => {
        fetch(`http://localhost:8181/api/facultad/delete/${id}`, { method: 'DELETE' })
            .then(() => setFacultades(facultades.filter(f => f.id !== id)));
    };

    return (
        <Container sx={{ mt: 4 }}>
            <Grid container spacing={4}>
                {/* Formulario a la izquierda */}
                <Grid item xs={12} md={4} sx={{ pr: 3 }}>
                    <Paper elevation={4} style={{ padding: '20px' }}>
                        <h2>{editId ? 'Editar Facultad' : 'Agregar Facultad'}</h2>
                        <form onSubmit={handleSubmit}>
                            <TextField
                                label="Nombre de la Facultad"
                                value={nombre}
                                onChange={(e) => setNombre(e.target.value)}
                                fullWidth
                                sx={{ mb: 2 }}
                            />
                            <TextField
                                label="Descripción"
                                value={descripcion}
                                onChange={(e) => setDescripcion(e.target.value)}
                                fullWidth
                                multiline
                                rows={3}
                                sx={{ mb: 2 }}
                            />
                            <Button type="submit" variant="contained" color="primary">
                                {editId ? 'Actualizar' : 'Guardar'}
                            </Button>
                        </form>
                    </Paper>
                </Grid>

                {/* Lista de facultades a la derecha */}
                <Grid item xs={12} md={8} sx={{ pl: 3 }}>
                    <Grid container spacing={2} direction="column">
                        {facultades.map((f) => (
                            <Grid item key={f.id}>
                                <Paper elevation={3} style={{ padding: '15px' }}>
                                    <h4>{f.nombre}</h4>
                                    <p>{f.descripcion}</p>
                                    <Button onClick={() => handleEdit(f)} variant="contained">
                                        Editar
                                    </Button>
                                    <Button
                                        onClick={() => handleDelete(f.id)}
                                        variant="outlined"
                                        color="error"
                                        style={{ marginLeft: 10 }}
                                    >
                                        Eliminar
                                    </Button>
                                </Paper>
                            </Grid>
                        ))}
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Facultad;
