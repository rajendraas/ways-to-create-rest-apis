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
class SunriseSunsetClientTest {

    public static MockWebServer mockWebServer;

    @Spy
    RestClient sunriseSunsetRestClient = RestClient.create(mockWebServer.url("/").toString());

    @InjectMocks
    SunriseSunsetClient sunriseSunsetClient;

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
    void shouldReturnSunriseSunsetDataOnSuccessfulApiCall() throws IOException, InterruptedException {
        String sunriseAndSunsetResponse = Files.readString(Path.of("src/test/resources/responses/sunriseAndSunsetResponse.json"));
        Object sunriseAndSunsetObject = objectMapper.readValue(sunriseAndSunsetResponse, Map.class);
        mockWebServer.enqueue(new MockResponse().setBody(sunriseAndSunsetResponse).addHeader("Content-Type", "application/json"));

        Object response = sunriseSunsetClient.getSunriseSunsetTimes("18.516726", "73.856255");

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/json?lat=18.516726&lng=73.856255&tzid=Asia/Kolkata", recordedRequest.getPath());
        assertEquals(sunriseAndSunsetObject, response);
    }
}