package com.israelbatista.bookstoremanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.israelbatista.bookstoremanager";
    private static final String TITLE_PROJECT = "Bookstore Manager";
    private static final String DESCRIPTION_PROJECT = "Bookstore Manager API Project";
    private static final String AUTHOR_PROJECT = "Israel Batista";
    private static final String GITHUB_PROJECT = "https://github.com/IBatistaSantos";
    private static final String EMAIL_AUTHOR = "israelbatiista19@gmail.com";
    private static final String API_VERSION = "1.0.0";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE_PROJECT)
                .description(DESCRIPTION_PROJECT)
                .version(API_VERSION)
                .contact(new Contact(AUTHOR_PROJECT, GITHUB_PROJECT, EMAIL_AUTHOR))
                .build();
    }
}
