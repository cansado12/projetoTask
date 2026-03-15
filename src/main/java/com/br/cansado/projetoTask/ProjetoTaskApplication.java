package com.br.cansado.projetoTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class ProjetoTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoTaskApplication.class, args);
	}

}
