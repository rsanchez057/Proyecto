import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, CircularProgress, Typography, TablePagination } from '@mui/material';

const Incidencias = () => {
  const [incidencias, setIncidencias] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  useEffect(() => {
    axios.get('http://localhost:8181/api/incidencia/all')
      .then(response => {
        setIncidencias(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error al obtener incidencias:', error);
        setLoading(false);
      });
  }, []);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  if (loading) return <div className="flex justify-center mt-4"><CircularProgress /></div>;

  return (
    <div className="p-4">
      <Typography variant="h4" gutterBottom>Lista de Incidencias</Typography>
      {incidencias.length === 0 ? (
        <Typography>No hay incidencias registradas.</Typography>
      ) : (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="tabla de incidencias">
            <TableHead>
              <TableRow>
                <TableCell><strong>Título</strong></TableCell>
                <TableCell><strong>Descripción</strong></TableCell>
                <TableCell><strong>Fecha</strong></TableCell>
                <TableCell><strong>Estado</strong></TableCell>
                <TableCell><strong>Tipo</strong></TableCell>
                <TableCell><strong>Alumno (CIF)</strong></TableCell>
                <TableCell><strong>Profesor (CIF)</strong></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {incidencias.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((incidencia, index) => (
                <TableRow key={index}>
                  <TableCell>{incidencia.titulo}</TableCell>
                  <TableCell>{incidencia.descripcion}</TableCell>
                  <TableCell>{incidencia.fecha}</TableCell>
                  <TableCell>{incidencia.estado}</TableCell>
                  <TableCell>{incidencia.tipo}</TableCell>
                  <TableCell>{incidencia.cifAlumno}</TableCell>
                  <TableCell>{incidencia.cifProfesor}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={incidencias.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </TableContainer>
      )}
    </div>
  );
};

export default Incidencias;
