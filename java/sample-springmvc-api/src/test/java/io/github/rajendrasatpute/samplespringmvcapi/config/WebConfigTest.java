package io.github.rajendrasatpute.samplespringmvcapi.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClient;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class WebConfigTest {
    @Autowired
    ApplicationContext context;

    @Test
    void shouldCreateOpenMeteoRestClientBean() {
        assertThatRestClientBeanExists("openMeteoRestClient");
    }

    @Test
    void shouldCreateSunriseSunsetRestClientBean() {
        assertThatRestClientBeanExists("sunriseSunsetRestClient");
    }

    private void assertThatRestClientBeanExists(String beanName) {
        assertTrue(context.containsBean(beanName));
        assertNotNull(context.getBean(beanName));
        assertTrue(Arrays.asList(context.getBean(beanName).getClass().getInterfaces()).contains(RestClient.class));
    }
}