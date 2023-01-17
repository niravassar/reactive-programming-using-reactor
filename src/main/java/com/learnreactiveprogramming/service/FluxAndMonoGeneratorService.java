package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .log(); // db or a remote service call
    }

    public Mono<String> nameMono() {

        return Mono.just("alex");
    }

    public Mono<String> namesMonoMapFilter(int stringLength) {

        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s-> s.length() == stringLength);
    }

    public Flux<String> namesFluxMap(int stringLength) {

        // filter the string whos length is greater then 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength) // 4-ALEX, 5-CHLOE
                .map(s->s.length() + "-"+s)
                .log(); // db or a remote service call
    }

    public Flux<String> namesFluxFlatMap(int stringLength) {

        // return the individual characters of the list
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap( s -> splitString(s))
                .log(); // db or a remote service call
    }

    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> namesFluxFlatMap_async(int stringLength) {

        // return the individual characters of the list
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap( s -> splitString_wthDelay(s))
                .log(); // db or a remote service call
    }

    public Flux<String> splitString_wthDelay(String name) {
        var charArray = name.split("");
       var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux()
                .subscribe( name -> {
                    System.out.println("Name is : " + name);
                });

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> {
                    System.out.println("Mono name is : " + name);
                });
    }
}
