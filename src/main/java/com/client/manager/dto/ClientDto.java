package com.client.manager.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ClientDto(
        String clienteid,
        @NotBlank String nombre,
        String genero,
        String edad,
        @NotBlank String identificacion,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @NotBlank String contrasena,
        String estado
) {
}
