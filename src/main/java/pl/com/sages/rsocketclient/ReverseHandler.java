package pl.com.sages.rsocketclient;

import org.springframework.messaging.handler.annotation.MessageMapping;
import reactor.core.publisher.Mono;

public class ReverseHandler {

    @MessageMapping("client.reverse.book")
    public Mono<Book> clientReverseBook(Mono<Book> bookMono) {
        return bookMono.map(book -> Book.builder().title(book.getTitle().repeat(2)).author(book.getAuthor()).build());
    }

}
