package com.client.manager.dto;

import com.client.manager.domain.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDto toDto(Client client) {
        return new ClientDto(
                client.getClienteId(),
                client.getNombre(),
                client.getGenero(),
                client.getEdad(),
                client.getIdentificacion(),
                client.getDireccion(),
                client.getTelefono(),
                client.getContrasena(),
                client.getEstado()
        );
    }

    public Client toEntity(ClientDto clientDto) {
        Client client = new Client();
        client.setClienteId(clientDto.clienteId());
        client.setNombre(clientDto.nombre());
        client.setGenero(clientDto.genero());
        client.setEdad(clientDto.edad());
        client.setIdentificacion(clientDto.identificacion());
        client.setDireccion(clientDto.direccion());
        client.setTelefono(clientDto.telefono());
        client.setContrasena(clientDto.contrasena());
        client.setEstado(clientDto.estado());
        return client;
    }
}
