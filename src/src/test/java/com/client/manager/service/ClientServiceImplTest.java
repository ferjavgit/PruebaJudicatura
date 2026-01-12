package com.client.manager.service;

import com.client.manager.domain.model.Client;
import com.client.manager.domain.repository.ClientRepository;
import com.client.manager.dto.ClientDto;
import com.client.manager.dto.ClientMapper;
import com.client.manager.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientDto clientDto;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        clientId = UUID.randomUUID();
        clientDto = new ClientDto(
                "testclient",
                "Test Name",
                "Test Gender",
                "30",
                "1234567890",
                "Test Address",
                "123456789",
                "password",
                "true"
        );
        client = new Client();
        client.setId(clientId);
        client.setClienteId(clientDto.clienteId());
        client.setNombre(clientDto.nombre());
        client.setGenero(clientDto.genero());
        client.setEdad(clientDto.edad());
        client.setIdentificacion(clientDto.identificacion());
        client.setDireccion(clientDto.direccion());
        client.setTelefono(clientDto.telefono());
        client.setContrasena(clientDto.contrasena());
        client.setEstado(clientDto.estado());
    }

    @Test
    void createClient_shouldReturnClientDto_whenClientIsSaved() {
        when(clientMapper.toEntity(any(ClientDto.class))).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto createdClient = clientService.createClient(clientDto);

        assertThat(createdClient).isNotNull();
        assertThat(createdClient.nombre()).isEqualTo(clientDto.nombre());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void getClientById_shouldReturnClientDto_whenClientExists() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientDto);

        ClientDto foundClient = clientService.getClientById(clientId);

        assertThat(foundClient).isNotNull();
        assertThat(foundClient.nombre()).isEqualTo(client.getNombre());
    }

    @Test
    void getClientById_shouldThrowResourceNotFoundException_whenClientDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(clientId));
    }

    @Test
    void getAllClients_shouldReturnListOfClientDtos() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientDto);

        List<ClientDto> clients = clientService.getAllClients();

        assertThat(clients).isNotEmpty();
        assertThat(clients.size()).isEqualTo(1);
        assertThat(clients.get(0).nombre()).isEqualTo(clientDto.nombre());
    }

    @Test
    void getAllClients_shouldReturnEmptyList_whenNoClientsExist() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        List<ClientDto> clients = clientService.getAllClients();

        assertThat(clients).isEmpty();
    }

    @Test
    void updateClient_shouldReturnUpdatedClientDto_whenClientExists() {
        ClientDto updatedDto = new ClientDto("updatedclient", "Updated Name", "Updated Gender", "40", "0987654321", "Updated Address", "987654321", "newpass", "false");
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(clientMapper.toDto(any(Client.class))).thenAnswer(invocation -> {
            Client savedClient = invocation.getArgument(0);
            return new ClientDto(
                    savedClient.getClienteId(),
                    savedClient.getNombre(),
                    savedClient.getGenero(),
                    savedClient.getEdad(),
                    savedClient.getIdentificacion(),
                    savedClient.getDireccion(),
                    savedClient.getTelefono(),
                    savedClient.getContrasena(),
                    savedClient.getEstado()
            );
        });

        ClientDto result = clientService.updateClient(clientId, updatedDto);

        assertThat(result).isNotNull();
        assertThat(result.nombre()).isEqualTo(updatedDto.nombre());
        assertThat(result.direccion()).isEqualTo(updatedDto.direccion());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void updateClient_shouldThrowResourceNotFoundException_whenClientDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.updateClient(clientId, clientDto));
    }

    @Test
    void deleteClient_shouldCompleteSuccessfully_whenClientExists() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(client);

        clientService.deleteClient(clientId);

        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    void deleteClient_shouldThrowResourceNotFoundException_whenClientDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(clientId));
    }
}
