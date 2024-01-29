package com.sha.rabbitdemo.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

public class MonoFluxTest {

    /**
     * Subscriber Subscribe to a Mono
     * Publisher Returns Subscription
     * Subscriber sends request(n)
     * Publisher sends onNext(n) or onComplete() or onError()
     */
    @Test
    public void testMono() {
        Mono<String> mono = Mono.just("Hello").log();
        mono.subscribe(System.out::println, System.err::println, () -> System.out.println("Completed"));
    }

    @Test
    public void testFlux() {
        Flux<List<String>> flux = Flux.just("Hi", "Hello", "Good morning")
                .bufferUntilChanged("Good morning"::equals)
                .concatWithValues(List.of("SOH"))
                .log();

        flux.subscribe(System.out::println, System.err::println, () -> System.out.println("Completed"));
    }
}
