package spring.rest.shop.springrestshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@Import(SecurityConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {
}
