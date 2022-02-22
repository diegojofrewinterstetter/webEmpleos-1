package com.webempleos.app.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autoridades")
public class Autoridad implements Serializable {


    private static final long serialVersionUID = 8821560880861712094L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String autoridad;

    public Autoridad(Integer id, String autoridad) {
        this.id = id;
        this.autoridad = autoridad;
    }

    @ManyToMany(mappedBy = "autoridades")
    private List<Usuario> usuarios;
}
