package pl.com.sages.rsocketclient;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookRestController {
    private final RSocketRequester rSocketRequester;

    @GetMapping("/{title}")
    public Mono<Book> findBook(@PathVariable("title") String title) {
        return rSocketRequester
                .route("find.book")
                .data(BookQueryDto.builder().title(title).build())
                .retrieveMono(Book.class);
    }

    @PostMapping
    public Mono<Void> acceptBook(@RequestBody Book book) {
        return rSocketRequester
                .route("accept.book")
                .data(book)
                .send();
    }

    @PostMapping("/reverse")
    public Flux<Book> reverseBooks() {
        return rSocketRequester
                .route("reverse.books")
                .data(Flux.just(Book.builder().author("Mickiewicz").title("Dziady").build(), Book.builder().author("Slowacki").title("Kordian").build()))
                .retrieveFlux(Book.class);
    }


}
