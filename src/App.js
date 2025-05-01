import React from 'react';
import './App.css';
import Appbar from './components/Appbar';
import AdminDashboard from './components/AdminDashboard';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#d32f2f',
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div className="App">
        <Appbar />
        <AdminDashboard />
      </div>
    </ThemeProvider>
  );
}

export default App;
