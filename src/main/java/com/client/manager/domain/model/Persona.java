package com.client.manager.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String nombre;
    private String genero;
    private String edad;
    private String identificaccion;
    private String direccion;
    private String telefono;

}
