package com.webempleos.app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "publicaciones")
public class Publicacion implements Serializable {

    private static final long serialVersionUID = 8910657312629655638L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String titulo;
    @NotEmpty
    private String descripcion;
    @Lob
    private byte[] imagen;
    @NotEmpty
    private String disponibilidad;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_categoria",referencedColumnName = "id")
    private Categoria categoria;
}
