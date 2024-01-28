package com.sha.rabbitdemo.reactor;

import org.junit.Test;
import reactor.core.publisher.Mono;

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
        mono.subscribe(System.out::println);
    }
}
