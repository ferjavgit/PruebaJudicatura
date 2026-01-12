package com.client.manager.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientDto(
        String clienteId,
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
