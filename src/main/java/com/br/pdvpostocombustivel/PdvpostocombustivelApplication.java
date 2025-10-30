package com.br.pdvpostocombustivel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "PDV Posto de Combustível API", version = "1.0", description = "API para o sistema de PDV de Posto de Combustível"))
public class PdvpostocombustivelApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdvpostocombustivelApplication.class, args);
	}
}
