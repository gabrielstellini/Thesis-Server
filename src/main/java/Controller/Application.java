package Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EnableJpaRepositories(basePackages = {"Model", "API","Service","Repositories"})
@EntityScan(basePackages = {"Model", "API", "Service", "Repositories"})
@ComponentScan({"Model","API", "Service", "Repositories"})

@EnableAutoConfiguration
@PropertySources({
        @PropertySource("classpath:auth0.properties")
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}