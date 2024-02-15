package io.github.rajendrasatpute.samplespringmvcapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("io.github.rajendrasatpute.samplespringmvcapi")
public class WebConfig {

    @Value("${client.openmeteo.domain}")
    private String openMeteoDomain;

    @Value("${client.sunsetsunrise.domain}")
    private String sunsetSunriseDomain;

    @Bean
    public RestClient openMeteoRestClient() {
        return RestClient.create(openMeteoDomain);
    }

    @Bean
    public RestClient sunriseSunsetRestClient() {
        return RestClient.create(sunsetSunriseDomain);
    }
}
