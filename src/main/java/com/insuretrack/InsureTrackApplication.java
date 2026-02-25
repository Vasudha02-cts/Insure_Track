package com.insuretrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class InsureTrackApplication {
    public static void main(String[] args) {
        SpringApplication.run(InsureTrackApplication.class, args);
    }
}
