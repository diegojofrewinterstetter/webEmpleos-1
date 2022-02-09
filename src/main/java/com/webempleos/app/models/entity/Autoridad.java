package com.webempleos.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "autoridades")
public class Autoridad implements Serializable {


    private static final long serialVersionUID = 8821560880861712094L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String autoridad;
}
