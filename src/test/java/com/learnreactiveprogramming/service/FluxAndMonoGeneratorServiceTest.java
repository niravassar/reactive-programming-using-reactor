package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void namesMono() {
        var namesMono = fluxAndMonoGeneratorService.nameMono();

        StepVerifier.create(namesMono)
                .expectNext("alex")
                .verifyComplete();
    }
}
