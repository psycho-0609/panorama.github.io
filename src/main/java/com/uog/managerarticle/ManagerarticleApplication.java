package com.uog.managerarticle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ManagerarticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerarticleApplication.class, args);

    }

}
