package com.alkemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringEduManagerApplication {

    public static void main(String[] args) {
        // Esta línea lanza toda la magia de Spring Boot, 
        // incluyendo el servidor Tomcat interno y la conexión a H2.
        SpringApplication.run(SpringEduManagerApplication.class, args);
    }
}