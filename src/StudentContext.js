import React, { createContext, useState } from 'react';

export const StudentContext = createContext();

export const StudentProvider = ({ children }) => {
    const [students, setStudents] = useState([
        { id: 1, name: 'John Doe', address: '123 Main St' },
        { id: 2, name: 'Jane Smith', address: '456 Elm St' },
    ]);

    return (
        <StudentContext.Provider value={{ students, setStudents }}>
            {children}
        </StudentContext.Provider>
    );
};