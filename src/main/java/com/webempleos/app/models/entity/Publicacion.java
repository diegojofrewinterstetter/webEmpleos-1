package com.webempleos.app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private String titulo;
    @NotBlank
    private String descripcion;
    @Lob
    private byte[] imagen;
    @NotBlank
    private String disponibilidad;
    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;

    @PrePersist
    public void prePersist() {
        this.fechaPublicacion = LocalDate.now();
    }
}
