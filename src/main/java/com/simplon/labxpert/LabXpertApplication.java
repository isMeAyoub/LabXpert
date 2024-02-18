package com.simplon.labxpert;

import com.simplon.labxpert.model.dto.RoleDTO;
import com.simplon.labxpert.model.dto.UserDTO;
import com.simplon.labxpert.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class of the application.
 * @EnableScheduling is used to enable the scheduling of the tasks like the check of the expiration date of the reagents.
 * @ComponentScan is used to scan the packages of the application.
 * @EnableSwagger2 is used to enable the Swagger documentation.
 * @Author Chaimaa Mahy and Ayoub Ait Si Ahmad
 */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@ComponentScan(basePackages = {"com.simplon.labxpert", "com.simplon.labxpert.config"})
public class LabXpertApplication {
    public static void main(String[] args) {
        SpringApplication.run(LabXpertApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
