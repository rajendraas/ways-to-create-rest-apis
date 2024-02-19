package io.github.rajendrasatpute.samplespringmvcapi.controller;

import io.github.rajendrasatpute.samplespringmvcapi.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    @Mock
    private CityService cityService;


    @Test
    void shouldReturnSuccessResponseWhenThereIsNoError() throws Exception {
        MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();

        mockMvc.perform(get("/city/pune")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenWrongApiIsCalled() throws Exception {
        MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();

        mockMvc.perform(get("/city")).andExpect(status().isNotFound());
    }

}