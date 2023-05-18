package spring.rest.shop.springrestshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@Import(SecurityConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/news").setViewName("news");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/"); // Удалите "**" из пути
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/webjars/"); // Удалите "**" из пути
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/images/");
    }

}
