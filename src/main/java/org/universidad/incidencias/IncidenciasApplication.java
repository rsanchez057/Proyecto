package org.universidad.incidencias;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication  // escanea org.universidad.incidencias y subpaquetes
public class IncidenciasApplication {
    public static void main(String[] args) {
        SpringApplication.run(IncidenciasApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}