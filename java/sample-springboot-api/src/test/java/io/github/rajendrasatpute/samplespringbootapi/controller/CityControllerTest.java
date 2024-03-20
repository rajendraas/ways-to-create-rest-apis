package io.github.rajendrasatpute.samplespringbootapi.controller;

import io.github.rajendrasatpute.samplespringbootapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringbootapi.dto.NewCityRequest;
import io.github.rajendrasatpute.samplespringbootapi.service.CityService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @MockBean
    private CityService cityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNotFoundWhenWrongApiIsCalled() throws Exception {
        mockMvc.perform(get("/city/pune/details")).andExpect(status().isNotFound());
    }

    @Nested
    class GetCityInformation {
        @Test
        void shouldReturnSuccessResponseWhenThereIsNoError() throws Exception {
            when(cityService.getCityInfo("pune")).thenReturn(CityInfoResponse.builder().build());

            mockMvc.perform(get("/city/pune")).andExpect(status().isOk());
        }
    }

    @Nested
    class AddCity {

        @Test
        void shouldReturnBadRequestWhenRequestBodyIsMissing() throws Exception {
            mockMvc.perform(post("/city")).andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenCityNameIsMissing() throws Exception {
            mockMvc.perform(
                    post("/city").content("{\"latitude\":\"18.516726\",\"longitude\":\"73.856255\"}").contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenLongitudeIsMissing() throws Exception {
            mockMvc.perform(
                    post("/city").content("{\"latitude\":\"18.516726\",\"cityName\":\"Pune\"}").contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenLatitudeIsMissing() throws Exception {
            mockMvc.perform(
                    post("/city").content("{\"cityName\":\"Pune\",\"longitude\":\"73.856255\"}").contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenLongitudeIsInvalid() throws Exception {
            mockMvc.perform(
                    post("/city").content("{\"latitude\":\"18.516726\",\"longitude\":\"273.856255\",\"cityName\":\"Pune\"}").contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenLatitudeIsInvalid() throws Exception {
            mockMvc.perform(
                    post("/city").content("{\"latitude\":\"118.516726\",\"longitude\":\"73.856255\",\"cityName\":\"Pune\"}").contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnCreatedWhenRequestIsValid() throws Exception {
            NewCityRequest request = NewCityRequest.builder().cityName("Pune").latitude("18.516726").longitude("73.856255").build();
            doNothing().when(cityService).addCity(request);
            mockMvc.perform(
                    post("/city").content("{\"latitude\":\"18.516726\",\"longitude\":\"73.856255\",\"cityName\":\"Pune\"}").contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            verify(cityService, times(1)).addCity(request);
        }
    }
}