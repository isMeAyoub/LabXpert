package com.simplon.labxpert.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for Swagger.
 * api() method is used to create a Docket instance.
 * apiInfo() method is used to create an ApiInfo instance.
 *
 * @Author Chaimaa Mahy
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Method to create a Docket instance.
     *
     * @return Docket instance.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.simplon.labxpert"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Method to create an ApiInfo instance.
     *
     * @return ApiInfo instance.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("LabXpert Spring Boot REST API Documentation")
                .description("REST APIs For Managing a labo")
                .version("1.0")
                .build();
    }
}
