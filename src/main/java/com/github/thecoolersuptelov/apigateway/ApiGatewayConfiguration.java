package com.github.thecoolersuptelov.apigateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion-service/**").uri("lb://currency-conversion-service"))
                .route(p -> p.path("/currency-conversion-service-feign/**").uri("lb://currency-conversion-service"))
                .route(p->p.path("/cc/**")
                        .filters(f->f.rewritePath(
                                "/cc/(?<segment>.*)",
                                "/currency-conversion-service/${segment}"))
                        .uri("lb://currency-conversion-service")

                )
                .route(p->p.path("/ce/**")
                        .filters(f->f.rewritePath(
                                "/cc/(?<segment>.*)",
                                "/currency-exchange/${segment}"))
                        .uri("lb://currency-exchange")

                )
                .build();
    }

}
