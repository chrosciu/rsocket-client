package pl.com.sages.rsocketclient;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Book;

@Component
@RequiredArgsConstructor
@RestController
@RequestMapping("/numbers")
public class NumberRestController {
    private final Mono<RSocketRequester> rSocketRequester;

    @GetMapping("/double/{number}")
    public Mono<Integer> doubleNumber(@PathVariable("number") Integer number) {
        return rSocketRequester.flatMap(requester -> requester
                .route("double.number")
                .data(number)
                .retrieveMono(Integer.class)
        );
    }

    @GetMapping("/accept/{number}")
    public Mono<Void> acceptNumber(@PathVariable("number") Integer number) {
        return rSocketRequester.flatMap(requester -> requester
                .route("accept.number")
                .data(number)
                .send()
        );
    }
}
