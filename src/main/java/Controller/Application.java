package Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"Model", "API","Service"})
@EntityScan(basePackages = {"Model", "API", "Service"})
@ComponentScan({"Model","API", "Service"})


@SpringBootApplication
@EnableOAuth2Sso // Without this, basic authentication is invoked
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}