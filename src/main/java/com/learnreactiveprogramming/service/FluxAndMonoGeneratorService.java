package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Function;

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
                .flatMap( s -> splitString_withDelay(s))
                .log(); // db or a remote service call
    }

    public Flux<String> splitString_withDelay(String name) {
        var charArray = name.split("");
       var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> namesFluxConcatMap(int stringLength) {

        // return the individual characters of the list
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .concatMap( s -> splitString_withDelay(s))
                .log(); // db or a remote service call
    }

    public Mono<List<String>> namesMono_flatMap(int stringLength) {

        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s-> s.length() == stringLength)
                .flatMap(this::splitStringMono);
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public Flux<String> namesMono_flatMap_many(int stringLength) {

        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s-> s.length() == stringLength)
                .flatMapMany(this::splitString)
                .log();
    }

    public Flux<String> namesFlux_transform(int stringLength) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);

        // return the individual characters of the list
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .flatMap( s -> splitString(s))
                .defaultIfEmpty("default")
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap( s -> splitString(s));

        var defaultFlux =  Flux.just("default")
                .transform(filterMap);

        // return the individual characters of the list
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .switchIfEmpty(defaultFlux)
                .log(); // db or a remote service call
    }

    public Flux<String> explore_concat() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, defFlux);
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
