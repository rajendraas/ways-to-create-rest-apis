package io.github.rajendrasatpute.samplespringmvcapi.config;

import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.springdoc", "io.github.rajendrasatpute.samplespringmvcapi"})
@Import({SpringDocConfiguration.class,
        SpringDocWebMvcConfiguration.class,
        org.springdoc.webmvc.ui.SwaggerConfig.class,
        SwaggerUiConfigProperties.class,
        SwaggerUiOAuthProperties.class,
        JacksonAutoConfiguration.class})
public class WebConfig implements WebMvcConfigurer {

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
