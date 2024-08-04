package com.esosa.password_service.config;

import com.esosa.password_service.client.AuthClient;
import com.esosa.password_service.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl("http://user-service") // URL base para UserService
                .filter(filterFunction)
                .build();
    }

    @Bean
    public WebClient authWebClient() {
        return WebClient.builder()
                .baseUrl("http://auth-service") // URL base para AuthService
                .filter(filterFunction)
                .build();
    }

    @Bean
    public UserClient userClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(WebClientAdapter.create(userWebClient()))
                .build();

        return httpServiceProxyFactory.createClient(UserClient.class);
    }

    @Bean
    public AuthClient authClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(WebClientAdapter.create(authWebClient()))
                .build();

        return httpServiceProxyFactory.createClient(AuthClient.class);
    }
}