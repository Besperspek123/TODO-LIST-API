package spring.rest.shop.springrestshop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class OpenAPILoader {

    private final ResourceLoader resourceLoader;

    public OpenAPILoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public OpenAPI load() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:openapi.yaml");
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        return yamlReader.readValue(resource.getInputStream(), OpenAPI.class);
    }
}