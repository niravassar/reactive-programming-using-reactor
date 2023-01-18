package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

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

    @Test
    void namesFluxMap() {
        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFluxMap(stringLength);

        StepVerifier.create(namesFlux)
                //.expectNext("ALEX", "BEN", "CHLOE")
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesMonoMapFilter() {
        int stringLength = 4;

        var namesFlux = fluxAndMonoGeneratorService.namesMonoMapFilter(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("ALEX")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMap() {
        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMap(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMap_async() {
        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMap_async(stringLength);

        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFluxConcatMap() {
        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFluxConcatMap(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMap() {
        int stringLength = 4;

        var namesMono = fluxAndMonoGeneratorService.namesMono_flatMap(stringLength);

        StepVerifier.create(namesMono)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMapMany() {
        int stringLength = 4;

        var namesMono = fluxAndMonoGeneratorService.namesMono_flatMap_many(stringLength);

        StepVerifier.create(namesMono)
                .expectNext("A", "L", "E", "X")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform() {
        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform_1() {
        int stringLength = 6;

        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void explore_concat() {

        var concatFlux = fluxAndMonoGeneratorService.explore_concat();

        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }
}
