package com.client.manager;

import com.client.manager.domain.model.Client;
import com.client.manager.domain.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientManagerApiApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ClientRepository clientRepository) {
		return args -> {
			Client cliente1 = new Client();
			cliente1.setClienteId("jlema");
			cliente1.setNombre("Jose Lema");
			cliente1.setGenero("Masculino");
			cliente1.setEdad("35");
			cliente1.setIdentificacion("1712345678");
			cliente1.setDireccion("Otavalo sn y principal");
			cliente1.setTelefono("098254785");
			cliente1.setContrasena("1234");
			cliente1.setEstado("True");
			clientRepository.save(cliente1);

			Client cliente2 = new Client();
			cliente2.setClienteId("mmontalvo");
			cliente2.setNombre("Marianela Montalvo");
			cliente2.setGenero("Femenino");
			cliente2.setEdad("28");
			cliente2.setIdentificacion("1787654321");
			cliente2.setDireccion("Amazonas y NNUU");
			cliente2.setTelefono("097548965");
			cliente2.setContrasena("5678");
			cliente2.setEstado("True");
			clientRepository.save(cliente2);

			Client cliente3 = new Client();
			cliente3.setClienteId("josorio");
			cliente3.setNombre("Juan Osorio");
			cliente3.setGenero("Masculino");
			cliente3.setEdad("42");
			cliente3.setIdentificacion("1700112233");
			cliente3.setDireccion("13 junio y Equinoccial");
			cliente3.setTelefono("098874587");
			cliente3.setContrasena("1245");
			cliente3.setEstado("True");
			clientRepository.save(cliente3);
		};
	}

}
