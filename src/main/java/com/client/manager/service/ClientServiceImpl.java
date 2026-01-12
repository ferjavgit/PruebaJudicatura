package com.client.manager.service;

import com.client.manager.domain.model.Client;
import com.client.manager.domain.model.Person;
import com.client.manager.domain.repository.ClientRepository;
import com.client.manager.dto.ClientDto;
import com.client.manager.dto.ClientMapper;
import com.client.manager.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    @Override
    public ClientDto getClientById(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return clientMapper.toDto(client);
    }

    @Override
    public List<ClientDto> getAllClients() {

        return clientRepository.findAll()
            .stream()
            .peek(client -> log.info("cliente: {} - uuid :{}", client.toString(), client.getId()))
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto updateClient(UUID id, ClientDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        client.setNombre(clientDto.nombre());
        client.setGenero(clientDto.genero());
        client.setEdad(clientDto.edad());
        client.setIdentificacion(clientDto.identificacion());
        client.setDireccion(clientDto.direccion());
        client.setTelefono(clientDto.telefono());
        client.setContrasena(clientDto.contrasena());
        client.setEstado(clientDto.estado());

        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDto(updatedClient);
    }

    @Override
    public void deleteClient(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        clientRepository.delete(client);
    }
}
