package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieReactiveServiceTest {


    private MovieInfoService movieInfoService = new MovieInfoService();
    private ReviewService reviewService = new ReviewService();

    MovieReactiveService movieReactiveService = new MovieReactiveService(movieInfoService, reviewService);

    @Test
    void getAllMovies() {

        var moviesInfo = movieReactiveService.getAllMovies();

        StepVerifier.create(moviesInfo)
                .assertNext(movieInfo -> {
                    assertEquals("Batman Begins", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);

                })
                .assertNext(movieInfo -> {
                    assertEquals("The Dark Knight", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);
                })
                .assertNext(movieInfo -> {
                    assertEquals("Dark Knight Rises", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);
                })
                .verifyComplete();
    }

    @Test
    void getMovieById() {

        long movieId = 100L;

        var movieMono = movieReactiveService.getMovieById(movieId).log();

        StepVerifier.create(movieMono)
                .assertNext(movieInfo -> {
                    assertEquals("Batman Begins", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);

                })
                .verifyComplete();


    }

    @Test
    void getMovieById_usingFlatMap() {

        //given
        long movieId = 1L;

        //when
        Mono<Movie> movieMono = movieReactiveService.getMovieByIdFlatMap(movieId);

        //then
        StepVerifier.create(movieMono)
                .assertNext(movieInfo -> {
                    assertEquals("Batman Begins", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);
                })
                .verifyComplete();
    }
}
