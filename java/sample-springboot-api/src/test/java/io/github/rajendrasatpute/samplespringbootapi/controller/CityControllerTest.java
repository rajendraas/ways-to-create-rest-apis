package io.github.rajendrasatpute.samplespringbootapi.controller;

import io.github.rajendrasatpute.samplespringbootapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringbootapi.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @MockBean
    private CityService cityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnSuccessResponseWhenThereIsNoError() throws Exception {
        when(cityService.getCityInfo("pune")).thenReturn(CityInfoResponse.builder().build());

        mockMvc.perform(get("/city/pune")).andExpect(status().isOk());
    }

}