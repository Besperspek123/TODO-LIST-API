package spring.rest.shop.springrestshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import spring.rest.shop.springrestshop.aspect.CurrentUserAspect;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "spring.rest.shop.springrestshop.aspect")
public class AspectConfig {

}
