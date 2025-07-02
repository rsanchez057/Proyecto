package org.universidad.incidencias;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.universidad.incidencias.repository")
@EntityScan(basePackages = "org.universidad.incidencias.model")
public class IncidenciasApplication {
    public static void main(String[] args) {
        SpringApplication.run(IncidenciasApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
