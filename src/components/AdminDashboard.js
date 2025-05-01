import React, { useState } from 'react';
import { Button, Container, Grid, Paper } from '@mui/material';
import Alumno from './Alumno';
import Profesor from './Profesor';
import Coordinador from './Coordinador';
import Facultad from './Facultad';

const AdminDashboard = () => {
    const [selectedComponent, setSelectedComponent] = useState(null);

    const renderComponent = () => {
        switch (selectedComponent) {
            case 'alumno':
                return <Alumno />;
            case 'profesor':
                return <Profesor />;
            case 'coordinador':
                return <Coordinador />;
            case 'facultad':
                return <Facultad />;
            default:
                return null;
        }
    };

    return (
        <Container>
            <Paper elevation={3} style={{ padding: '20px', marginBottom: '20px' }}>
                <Grid container spacing={2} justifyContent="center">
                    <Grid item>
                        <Button variant="contained" onClick={() => setSelectedComponent('alumno')}>Alumnos</Button>
                    </Grid>
                    <Grid item>
                        <Button variant="contained" onClick={() => setSelectedComponent('profesor')}>Profesores</Button>
                    </Grid>
                    <Grid item>
                        <Button variant="contained" onClick={() => setSelectedComponent('coordinador')}>Coordinadores</Button>
                    </Grid>
                    <Grid item>
                        <Button variant="contained" onClick={() => setSelectedComponent('facultad')}>Facultades</Button>
                    </Grid>
                </Grid>
            </Paper>

            {renderComponent()}
        </Container>
    );
};

export default AdminDashboard;
