package pl.com.sages.rsocketclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;

@Configuration
public class RSocketClientConfiguration {

    RSocketStrategies strategies = RSocketStrategies.builder()
            .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
            .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
            .build();

    @Bean
    RSocketRequester requester(RSocketRequester.Builder rsocketRequesterBuilder) {
        //return rsocketRequesterBuilder.connectTcp("localhost", 7000).block();
        return rsocketRequesterBuilder
                .rsocketFactory(RSocketMessageHandler.clientResponder(strategies, new ReverseHandler()))
                .connectTcp("localhost", 7000).block();
    }
}
