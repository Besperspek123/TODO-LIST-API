package spring.rest.shop.springrestshop.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SwaggerConfig {

    private final OpenAPILoader openAPILoader;

    public SwaggerConfig(OpenAPILoader openAPILoader) {
        this.openAPILoader = openAPILoader;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("spring")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi() throws IOException {
        return openAPILoader.load();
    }
}
