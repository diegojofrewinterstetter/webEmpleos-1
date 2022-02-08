package com.webempleos.app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1901544997042742413L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer documento;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String email;
    private boolean alta;
    private Byte[] imagen;
    private String educacion;
    private String descripcion;

}
