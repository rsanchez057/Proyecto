package org.universidad.incidencias.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coordinador", uniqueConstraints = @UniqueConstraint(columnNames = "cif"))
public class Coordinador extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;


    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final Rol rol = Rol.COORDINADOR;
}
