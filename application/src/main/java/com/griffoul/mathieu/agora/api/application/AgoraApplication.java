package com.griffoul.mathieu.agora.api.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.griffoul.mathieu.agora")
@EntityScan("com.griffoul.mathieu.agora.api.data.data.model")
public class AgoraApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgoraApplication.class, args);
    }

}
