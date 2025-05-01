import React, { useState, useEffect } from 'react';
import { Container, Paper, TextField, Button, Grid } from '@mui/material';

const Coordinador = () => {
    const paperStyle = { padding: '50px 20px', width: 600, margin: '20px auto' };
    const [cif, setCif] = useState('');
    const [nombres, setNombres] = useState('');
    const [apellido, setApellido] = useState('');
    const [email, setEmail] = useState('');
    const [facultad, setFacultad] = useState('');
    const [coordinadores, setCoordinadores] = useState([]);
    const [currentCoordinador, setCurrentCoordinador] = useState(null);
    const [editMode, setEditMode] = useState(false);

    // Cargar todos los coordinadores desde el backend
    useEffect(() => {
        fetch('http://localhost:8181/api/coordinador/all')
            .then((res) => res.json())
            .then((result) => {
                setCoordinadores(result);
            });
    }, []);

    // Manejar el submit de un nuevo coordinador
    const handleSubmit = (e) => {
        e.preventDefault();
        const coordinador = { cif, nombres, apellido, email, facultad };
        fetch('http://localhost:8181/api/coordinador/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(coordinador),
        }).then(() => {
            setCoordinadores([...coordinadores, coordinador]); // Actualiza la lista de coordinadores
            setCif(''); setNombres(''); setApellido(''); setEmail(''); setFacultad('');
        });
    };

    // Manejar la edición de un coordinador
    const handleEdit = (coordinador) => {
        setCurrentCoordinador(coordinador);
        setCif(coordinador.cif);
        setNombres(coordinador.nombres);
        setApellido(coordinador.apellido);
        setEmail(coordinador.email);
        setFacultad(coordinador.facultad);
        setEditMode(true);
    };

    // Manejar la actualización de un coordinador
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
            setCif(''); setNombres(''); setApellido(''); setEmail(''); setFacultad('');
        });
    };

    // Manejar la eliminación de un coordinador
    const handleDelete = (cif) => {
        fetch(`http://localhost:8181/api/coordinador/delete/${cif}`, {
            method: 'DELETE',
        }).then(() => {
            setCoordinadores(coordinadores.filter((c) => c.cif !== cif));
        });
    };

    return (
        <Container>
            <Paper elevation={3} style={paperStyle}>
                <h1 style={{ color: 'blue' }}>
                    <u>{editMode ? 'Editar Coordinador' : 'Agregar Coordinador'}</u>
                </h1>
                <form noValidate autoComplete="off" onSubmit={editMode ? handleUpdate : handleSubmit}>
                    <TextField
                        label="CIF"
                        variant="outlined"
                        fullWidth
                        value={cif}
                        onChange={(e) => setCif(e.target.value)}
                        sx={{ marginBottom: '16px' }}
                    />
                    <TextField
                        label="Nombres"
                        variant="outlined"
                        fullWidth
                        value={nombres}
                        onChange={(e) => setNombres(e.target.value)}
                        sx={{ marginBottom: '16px' }}
                    />
                    <TextField
                        label="Apellido"
                        variant="outlined"
                        fullWidth
                        value={apellido}
                        onChange={(e) => setApellido(e.target.value)}
                        sx={{ marginBottom: '16px' }}
                    />
                    <TextField
                        label="Email"
                        variant="outlined"
                        fullWidth
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        sx={{ marginBottom: '16px' }}
                    />
                    <TextField
                        label="Facultad"
                        variant="outlined"
                        fullWidth
                        value={facultad}
                        onChange={(e) => setFacultad(e.target.value)}
                        sx={{ marginBottom: '16px' }}
                    />
                    <Button variant="contained" color="primary" type="submit">
                        {editMode ? 'Actualizar' : 'Agregar'}
                    </Button>
                </form>
            </Paper>

            <Grid container spacing={2} style={{ marginTop: '20px' }}>
                {coordinadores.map((coordinador) => (
                    <Grid item xs={12} sm={6} md={4} key={coordinador.cif}>
                        <Paper elevation={3} style={{ padding: '10px', margin: '10px' }}>
                            <h3>{coordinador.nombres} {coordinador.apellido}</h3>
                            <Button
                                variant="contained"
                                color="secondary"
                                onClick={() => handleEdit(coordinador)}
                                style={{ marginRight: '10px' }}
                            >
                                Editar
                            </Button>
                            <Button
                                variant="contained"
                                color="error"
                                onClick={() => handleDelete(coordinador.cif)}
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

export default Coordinador;
