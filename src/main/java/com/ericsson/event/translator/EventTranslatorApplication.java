package com.ericsson.event.translator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EventTranslatorApplication {

    /**
     * Instantiate new RestTemplate Object.
     * @return restTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Starts the Springboot application.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EventTranslatorApplication.class, args);
    }

}
