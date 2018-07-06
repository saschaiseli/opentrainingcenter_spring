package ch.opentrainingcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class OpentrainingcenterApplication {

    public static void main(final String[] args) {
        SpringApplication.run(OpentrainingcenterApplication.class, args);
    }
}
