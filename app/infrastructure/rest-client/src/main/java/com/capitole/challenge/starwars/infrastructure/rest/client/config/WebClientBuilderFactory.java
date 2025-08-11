package com.capitole.challenge.starwars.infrastructure.rest.client.config;

import com.capitole.challenge.starwars.infrastructure.rest.client.config.WebClientConfigProperties.WebClientProperties;
import io.micrometer.observation.ObservationRegistry;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.netty.http.client.HttpClient;

/**
 * @author alex.vall
 *
 * @since 1.0.0
 * Factory used to build the web client
 */
@Data
@Slf4j
@Component
public class WebClientBuilderFactory {

  private final ApplicationContext applicationContext;
  private final WebClientConfigProperties configProperties;

  public Builder getWebClientBuilder(String clientId) {
    log.debug("build web client {}", clientId);
    WebClientProperties props = configProperties.getConfigs().get(clientId);
    if (props == null) {
      throw new IllegalArgumentException(
          "no clientId registered with name "
              + clientId
              + ". Check you application.yml \"capitol.common.web.client.configs."
              + clientId
              + "\"");
    }

    log.debug("web client props {}", props);

    Builder builder = WebClient.builder().baseUrl(props.getBaseUrl());
    setUp(builder, props);
    if (props.isExposeMetrics()) {
      builder
          .observationRegistry(ObservationRegistry.create())
          .observationConvention(new DefaultClientRequestObservationConvention());
    }
    return builder;
  }

  private void setUp(Builder builder, WebClientProperties props) {
    HttpClient httpClient = HttpClient.create();
    httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, props.getConnectTimeout() * 1000);
    httpClient.doOnConnected(
        conn ->
            conn.addHandlerLast(new ReadTimeoutHandler(props.getReadTimeout()))
                .addHandlerLast(new WriteTimeoutHandler(props.getWriteTimeout())));

    builder.clientConnector(new ReactorClientHttpConnector(httpClient.wiretap(props.isWiretap())));
  }


}
