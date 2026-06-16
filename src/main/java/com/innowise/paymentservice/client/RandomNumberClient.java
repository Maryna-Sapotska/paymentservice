package com.innowise.paymentservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class RandomNumberClient {

    private final WebClient webClient;

    public Integer getRandomNumber() {

        return webClient.get()
                .uri("https://www.randomnumberapi.com/api/v1.0/random?min=1&max=100&count=1")
                .retrieve()
                .bodyToMono(Integer[].class)
                .map(numbers -> numbers[0])
                .block();
    }
}
