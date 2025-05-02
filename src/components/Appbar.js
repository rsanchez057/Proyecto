import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Switch from '@mui/material/Switch';

export default function Appbar({ modoOscuro, setModoOscuro }) {
const handleToggle = () => {
    setModoOscuro(!modoOscuro);
};

return (
    <AppBar position="static">
    <Toolbar sx={{ justifyContent: 'flex-end' }}>
        <Switch
         checked={modoOscuro}
          onChange={handleToggle}
          inputProps={{ 'aria-label': 'toggle dark mode' }}
        />
    </Toolbar>
    </AppBar>
);
}
