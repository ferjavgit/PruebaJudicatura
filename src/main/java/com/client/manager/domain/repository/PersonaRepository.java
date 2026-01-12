package com.client.manager.domain.repository;

import com.client.manager.domain.model.Client;
import com.client.manager.domain.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {
}
