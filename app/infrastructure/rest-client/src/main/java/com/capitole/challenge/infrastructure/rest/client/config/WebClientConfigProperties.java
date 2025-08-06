package com.capitole.challenge.infrastructure.rest.client.config;

import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties class for a web client.
 *
 * @since 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "capitole.common.web.client")
public class WebClientConfigProperties {

  /**
   * Web Client configurations <code>
   * common.web.client.configs.unsecure.client-id=unsecure common.web.client.configs.unsecure.security=NONE
   * common.web.client.configs.unsecure.base-url=http://localhost:8080/public
   * </code>
   */
  private Map<String, WebClientProperties> configs = new HashMap<>();

  public enum WebClientSecurity {
    NONE,
    CLIENT_CREDENTIALS,
    AUTH_CODE_FLOW,
    PASSWORD_FLOW
  }

  @Data
  @Validated
  public static class WebClientProperties {

    /**
     * Security configured for this client. In case a security other than NONE is specified, then
     * the "spring.security.oauth2.client.registration.CLIENT_ID." config setting are expected.
     *
     * <p>In case CLIENT_CREDENTIALS is set <code>
     * spring.security.oauth2.client.registration.CLIENT_ID.authorization-grant-type=client_credentials
     * spring.security.oauth2.client.registration.CLIENT_ID.client-id=client-id
     * spring.security.oauth2.client.registration.CLIENT_ID.client-secret=client-secret
     * spring.security.oauth2.client.provider.CLIENT_ID.token-uri=http://localhost:8085/oauth/token
     * </code>
     *
     * <p>In case of AUTH_CODE_FLOW is set <code>
     * spring.security.oauth2.client.registration.CLIENT_ID.authorization-grant-type=authorization_code
     * spring.security.oauth2.client.registration.CLIENT_ID.client-id=client-id
     * spring.security.oauth2.client.registration.CLIENT_ID.client-name=client-id
     * spring.security.oauth2.client.registration.CLIENT_ID.client-secret=client-secret
     * spring.security.oauth2.client.registration.CLIENT_ID.redirect-uri=http://localhost:8085/oauth/token
     * spring.security.oauth2.client.provider.CLIENT_ID.token-uri=http://localhost:8085/oauth/token
     * spring.security.oauth2.client.provider.CLIENT_ID.authorization-uri=http://localhost:8085/oauth/authorize
     * spring.security.oauth2.client.provider.CLIENT_ID.user-info-uri=http://localhost:8084/user
     * spring.security.oauth2.client.provider.CLIENT_ID.user-name-attribute=name
     * </code>
     */
    private WebClientSecurity security = WebClientSecurity.NONE;

    /** Base url to be set */
    @NotEmpty private String baseUrl = null;

    /** Connect timeout in seconds */
    private int connectTimeout = 10;

    /** Read timeout in seconds */
    private int readTimeout = 10;

    /** Write timeout in seconds */
    private int writeTimeout = 10;

    /** True to log all requests */
    private boolean wiretap = true;
    /** True to expose metrics by using MetricsWebClientFilterFunction */
    private boolean exposeMetrics = true;
    /** Default user for WebClientSecurity.PASSWORD requests */
    private String defaultUser = "";
    /** Default password for WebClientSecurity.PASSWORD requests */
    private String defaultPassword = "";
  }
}
