import React, { useState } from 'react';
import { Box, Drawer, List, ListItem, ListItemButton, ListItemText, Typography, Toolbar, AppBar, CssBaseline } from '@mui/material';
import Alumno from './Alumno';
import Profesor from './Profesor';
import Coordinador from './Coordinador';
import Facultad from './Facultad';
import Incidencias from './Incidencias'; // ✅ Importar el componente

const drawerWidth = 240;

const AdminDashboard = () => {
    const [selectedComponent, setSelectedComponent] = useState('alumno');

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
            case 'incidencias':
                return <Incidencias />; // ✅ Agregado
            default:
                return null;
        }
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
                <Toolbar>
                    <Typography variant="h6" noWrap component="div">
                        Panel de Administración
                    </Typography>
                </Toolbar>
            </AppBar>

            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
                }}
            >
                <Toolbar />
                <Box sx={{ overflow: 'auto' }}>
                    <List>
                        {[
                            { label: 'Alumnos', key: 'alumno' },
                            { label: 'Profesores', key: 'profesor' },
                            { label: 'Coordinadores', key: 'coordinador' },
                            { label: 'Facultades', key: 'facultad' },
                            { label: 'Incidencias', key: 'incidencias' }, // ✅ Entrada nueva
                        ].map(({ label, key }) => (
                            <ListItem key={key} disablePadding>
                                <ListItemButton onClick={() => setSelectedComponent(key)}>
                                    <ListItemText primary={label} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                </Box>
            </Drawer>

            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    bgcolor: 'background.default',
                    p: 3,
                    ml: `${drawerWidth}px`,
                }}
            >
                <Toolbar />
                {renderComponent()}
            </Box>
        </Box>
    );
};

export default AdminDashboard;
