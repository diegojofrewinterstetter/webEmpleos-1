package com.webempleos.app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
    @Lob
    private byte[] imagen;
    private String educacion;
    private String descripcion;

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "usuario")
    private List<Publicacion> publicaciones;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_habilidad")
    private Habilidad habilidad;

    @ManyToMany
    @JoinTable(name = "usuarios_autoridades", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_autoridad", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario", "id_autoridad"})})
    private List<Autoridad> autoridades;
}
