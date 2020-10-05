package pl.com.sages.rsocketclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value="chat.enabled", havingValue = "true")
public class NumberChat implements CommandLineRunner {
    private final Mono<RSocketRequester> rSocketRequester;

    @Override
    public void run(String... args) {
        Flux<Integer> outbound = Flux.interval(Duration.ofSeconds(8)).map(l -> (int)(long)l);
        Flux<Integer> inbound = rSocketRequester.flatMapMany(requester -> requester
                .route("chat.number")
                .data(outbound)
                .retrieveFlux(Integer.class)
        );
        inbound.subscribe(i -> log.info("inbound: " + i), e -> log.warn("", e));

    }
}
