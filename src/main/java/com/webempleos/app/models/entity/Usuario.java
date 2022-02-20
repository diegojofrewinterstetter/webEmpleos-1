package com.webempleos.app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @NotNull
    @Max(value = 100000000)
    private Integer documento;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
    private boolean alta;
    @Lob
    private byte[] imagen;
    private String educacion;
    private String descripcion;

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "usuario",fetch = FetchType.LAZY)
    private List<Publicacion> publicaciones;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_habilidad")
    private Habilidad habilidad;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "usuarios_autoridades", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_autoridad", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario", "id_autoridad"})})
    private List<Autoridad> autoridades;

    {
        this.autoridades = new ArrayList<>();
        this.publicaciones=new ArrayList<>();
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    @PrePersist
    public void prePersist() {
        this.alta = true;
    }
}
