package io.github.rajendrasatpute.samplespringmvcapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OpenMeteoClientTest {

    public static MockWebServer mockWebServer;

    @Spy
    RestClient openMeteoRestClient = RestClient.create(mockWebServer.url("/").toString());

    @InjectMocks
    OpenMeteoClient openMeteoClient;

    @InjectMocks
    ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldReturnWeatherDataOnSuccessfulApisCall() throws IOException, InterruptedException {
        String weatherResponse = Files.readString(Path.of("src/test/resources/responses/weatherResponse.json"));
        Object weatherObject = objectMapper.readValue(weatherResponse, Map.class);
        mockWebServer.enqueue(new MockResponse().setBody(weatherResponse).addHeader("Content-Type", "application/json"));

        Object response = openMeteoClient.getWeather("18.516726", "73.856255");

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/forecast?current=temperature_2m,wind_speed_10m&latitude=18.516726&longitude=73.856255&timezone=IST", recordedRequest.getPath());
        assertEquals(weatherObject, response);
    }

}