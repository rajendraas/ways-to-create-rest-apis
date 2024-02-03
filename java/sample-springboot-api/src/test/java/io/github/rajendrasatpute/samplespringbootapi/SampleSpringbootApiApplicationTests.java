package io.github.rajendrasatpute.samplespringbootapi;

import io.github.rajendrasatpute.samplespringbootapi.controller.CityController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SampleSpringbootApiApplicationTests {

    @Autowired
    private CityController cityController;

    @Test
    void contextLoads() {
        assertThat(cityController).isNotNull();
    }

}
