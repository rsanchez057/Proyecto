// src/api/alumnos.js

const BASE_URL = 'http://localhost:8181'; // Cambia esto si usas otro puerto

export async function getAlumnos() {
const res = await fetch(`${BASE_URL}/alumnos`);
if (!res.ok) throw new Error('Error al obtener alumnos');
return await res.json();
}

export async function createAlumno(alumno) {
const res = await fetch(`${BASE_URL}/alumnos`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(alumno),
});
if (!res.ok) throw new Error('Error al crear alumno');
return await res.json();
}

// También puedes agregar updateAlumno y deleteAlumno si los necesitas
