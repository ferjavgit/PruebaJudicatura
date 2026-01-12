package com.client.manager.controller;

import com.client.manager.dto.ClientDto;
import com.client.manager.exception.GlobalExceptionHandler;
import com.client.manager.exception.ResourceNotFoundException;
import com.client.manager.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ObjectMapper objectMapper;

    private ClientDto clientDto;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

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
    }

    @Test
    void getAllClients_shouldReturnListOfClients() throws Exception {
        given(clientService.getAllClients()).willReturn(List.of(clientDto));

        mockMvc.perform(get("/api/v1/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value(clientDto.nombre()));
    }

    @Test
    void getClientById_shouldReturnClient_whenClientExists() throws Exception {
        given(clientService.getClientById(clientId)).willReturn(clientDto);

        mockMvc.perform(get("/api/v1/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(clientDto.nombre()));
    }

    @Test
    void getClientById_shouldReturnNotFound_whenClientDoesNotExist() throws Exception {
        given(clientService.getClientById(clientId)).willThrow(new ResourceNotFoundException("Client not found"));

        mockMvc.perform(get("/api/v1/clients/{id}", clientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createClient_shouldReturnCreatedClient() throws Exception {
        given(clientService.createClient(any(ClientDto.class))).willReturn(clientDto);

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value(clientDto.nombre()));
    }

    @Test
    void createClient_shouldReturnBadRequest_whenDtoIsInvalid() throws Exception {
        ClientDto invalidDto = new ClientDto(null, "", null, null, "", "", "", "", null);

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateClient_shouldReturnUpdatedClient() throws Exception {
        given(clientService.updateClient(eq(clientId), any(ClientDto.class))).willReturn(clientDto);

        mockMvc.perform(put("/api/v1/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(clientDto.nombre()));
    }

    @Test
    void updateClient_shouldReturnNotFound_whenClientDoesNotExist() throws Exception {
        given(clientService.updateClient(eq(clientId), any(ClientDto.class)))
                .willThrow(new ResourceNotFoundException("Client not found"));

        mockMvc.perform(put("/api/v1/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteClient_shouldReturnNoContent_whenClientExists() throws Exception {
        doNothing().when(clientService).deleteClient(clientId);

        mockMvc.perform(delete("/api/v1/clients/{id}", clientId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteClient_shouldReturnNotFound_whenClientDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException("Client not found")).when(clientService).deleteClient(clientId);

        mockMvc.perform(delete("/api/v1/clients/{id}", clientId))
                .andExpect(status().isNotFound());
    }
}
