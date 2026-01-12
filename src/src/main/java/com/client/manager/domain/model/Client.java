package com.client.manager.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Client extends Person {

    @Column(unique = true)
    private String clienteId;
    private String contrasena;
    private String estado;

}
