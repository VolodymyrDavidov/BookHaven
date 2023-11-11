package com.project.bookhaven;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookHavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookHavenApplication.class, args);
    }

    @Bean
    public CommandLineRunner myCommandLineRunner() {
        return args -> System.out.println("Hello from CommandLineRunner!");
    }

}
