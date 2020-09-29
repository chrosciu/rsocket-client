package pl.com.sages.rsocketclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class RSocketClientConfiguration {
    @Bean
    Mono<RSocketRequester> requester(RSocketRequester.Builder rSocketRequesterBuilder) {
        return rSocketRequesterBuilder
                .connectTcp("localhost", 7000);
    }
}
