package com.client.manager.service;

import com.client.manager.dto.ClientDto;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ClientDto createClient(ClientDto clientDto);

    ClientDto getClientById(UUID id);

    List<ClientDto> getAllClients();

    ClientDto updateClient(UUID id, ClientDto clientDto);

    void deleteClient(UUID id);
}
