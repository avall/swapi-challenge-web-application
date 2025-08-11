package com.capitole.challenge.starwars.infrastructure.rest.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.spring.webflux.LogbookExchangeFilterFunction;

@Configuration
@ComponentScan("com.capitole.challenge.starwars.infrastructure.rest.client.config")
@RequiredArgsConstructor
public class InfrastructureRestClientAutoConfiguration {

  private final WebClientBuilderFactory webClientBuilderFactory;

  @Bean
  Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper mapper) {
    return new Jackson2JsonEncoder(mapper);
  }

  @Bean
  Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper mapper) {
    return new Jackson2JsonDecoder(mapper);
  }

  @Bean("starwarsWebClient")
  public WebClient starwarsWebClient(Logbook logbook, Jackson2JsonEncoder encoder, Jackson2JsonDecoder decoder) {
    ExchangeStrategies exchangeStrategies =
        ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().jackson2JsonEncoder(encoder))
            .codecs(configurer -> configurer.defaultCodecs().jackson2JsonDecoder(decoder))
            .build();

    return webClientBuilderFactory
        .getWebClientBuilder("starwars-service")
        .filter(new LogbookExchangeFilterFunction(logbook))
        .exchangeStrategies(exchangeStrategies)
        .build();
  }
}
