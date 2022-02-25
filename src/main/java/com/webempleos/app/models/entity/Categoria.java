package com.webempleos.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable{

    private static final long serialVersionUID = -4047008400820961305L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String nombre;

    @OneToMany(cascade = CascadeType.MERGE,mappedBy = "categoria")
    private List<Publicacion> publicaciones = new ArrayList<>();

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}
