import React, { useState, useEffect } from 'react';
import { Container, Paper, TextField, Button, Grid, Box } from '@mui/material';

const Coordinador = () => {
    const [cif, setCif] = useState('');
    const [nombres, setNombres] = useState('');
    const [apellido, setApellido] = useState('');
    const [email, setEmail] = useState('');
    const [facultad, setFacultad] = useState('');
    const [coordinadores, setCoordinadores] = useState([]);
    const [currentCoordinador, setCurrentCoordinador] = useState(null);
    const [editMode, setEditMode] = useState(false);

    useEffect(() => {
        fetch('http://localhost:8181/api/coordinador/all')
            .then((res) => res.json())
            .then((result) => setCoordinadores(result));
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        const coordinador = { cif, nombres, apellido, email, facultad };
        fetch('http://localhost:8181/api/coordinador/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(coordinador),
        }).then(() => {
            setCoordinadores([...coordinadores, coordinador]);
            resetForm();
        });
    };

    const handleEdit = (coordinador) => {
        setCurrentCoordinador(coordinador);
        setCif(coordinador.cif);
        setNombres(coordinador.nombres);
        setApellido(coordinador.apellido);
        setEmail(coordinador.email);
        setFacultad(coordinador.facultad);
        setEditMode(true);
    };

    const handleUpdate = (e) => {
        e.preventDefault();
        const updatedCoordinador = { cif, nombres, apellido, email, facultad };
        fetch(`http://localhost:8181/api/coordinador/update/${currentCoordinador.cif}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedCoordinador),
        }).then(() => {
            setCoordinadores(coordinadores.map((c) => (c.cif === currentCoordinador.cif ? { ...c, ...updatedCoordinador } : c)));
            setEditMode(false);
            setCurrentCoordinador(null);
            resetForm();
        });
    };

    const handleDelete = (cif) => {
        fetch(`http://localhost:8181/api/coordinador/delete/${cif}`, {
            method: 'DELETE',
        }).then(() => {
            setCoordinadores(coordinadores.filter((c) => c.cif !== cif));
        });
    };

    const resetForm = () => {
        setCif('');
        setNombres('');
        setApellido('');
        setEmail('');
        setFacultad('');
    };

    return (
        <Container>
            <Grid container spacing={2}>
                {/* Formulario a la izquierda */}
                <Grid item xs={12} md={6}>
                    <Paper elevation={3} sx={{ padding: '30px' }}>
                        <h2 style={{ color: 'blue' }}>
                            <u>{editMode ? 'Editar Coordinador' : 'Agregar Coordinador'}</u>
                        </h2>
                        <form onSubmit={editMode ? handleUpdate : handleSubmit}>
                            <TextField label="CIF" variant="outlined" fullWidth value={cif} onChange={(e) => setCif(e.target.value)} sx={{ mb: 2 }} />
                            <TextField label="Nombres" variant="outlined" fullWidth value={nombres} onChange={(e) => setNombres(e.target.value)} sx={{ mb: 2 }} />
                            <TextField label="Apellido" variant="outlined" fullWidth value={apellido} onChange={(e) => setApellido(e.target.value)} sx={{ mb: 2 }} />
                            <TextField label="Email" variant="outlined" fullWidth value={email} onChange={(e) => setEmail(e.target.value)} sx={{ mb: 2 }} />
                            <TextField label="Facultad" variant="outlined" fullWidth value={facultad} onChange={(e) => setFacultad(e.target.value)} sx={{ mb: 2 }} />
                            <Button variant="contained" color="primary" type="submit" fullWidth>
                                {editMode ? 'Actualizar' : 'Agregar'}
                            </Button>
                        </form>
                    </Paper>
                </Grid>

                {/* Lista a la derecha con scroll */}
                <Grid item xs={12} md={6}>
                    <Paper elevation={3} sx={{ maxHeight: '600px', overflowY: 'auto', padding: 2 }}>
                        <h2 style={{ color: 'green' }}><u>Lista de Coordinadores</u></h2>
                        {coordinadores.map((coordinador) => (
                            <Box key={coordinador.cif} sx={{ mb: 2, p: 2, border: '1px solid #ccc', borderRadius: '8px' }}>
                                <h4>{coordinador.nombres} {coordinador.apellido}</h4>
                                <p><strong>Email:</strong> {coordinador.email}</p>
                                <p><strong>Facultad:</strong> {coordinador.facultad}</p>
                                <Button variant="contained" color="secondary" size="small" sx={{ mr: 1 }} onClick={() => handleEdit(coordinador)}>
                                    Editar
                                </Button>
                                <Button variant="contained" color="error" size="small" onClick={() => handleDelete(coordinador.cif)}>
                                    Eliminar
                                </Button>
                            </Box>
                        ))}
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Coordinador;
