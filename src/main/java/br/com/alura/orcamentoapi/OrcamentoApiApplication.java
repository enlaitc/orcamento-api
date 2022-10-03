package br.com.alura.orcamentoapi;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "BearerJWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OrcamentoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrcamentoApiApplication.class, args);
	}

}
