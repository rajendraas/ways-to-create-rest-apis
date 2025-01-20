package io.github.rajendrasatpute.samplespringmvcapi.controller;

import io.github.rajendrasatpute.samplespringmvcapi.dto.NewCityRequest;
import io.github.rajendrasatpute.samplespringmvcapi.dto.UpdateCityRequest;
import io.github.rajendrasatpute.samplespringmvcapi.service.CityService;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
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

		mockMvc.perform(get("/city/pune/details")).andExpect(status().isNotFound());
	}

	@Nested
	class AddCity {

		@Test
		void shouldReturnBadRequestWhenRequestBodyIsMissing() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(post("/city")).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenCityNameIsMissing() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(post("/city").content("{\"latitude\":\"18.516726\",\"longitude\":\"73.856255\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenLongitudeIsMissing() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(post("/city").content("{\"latitude\":\"18.516726\",\"cityName\":\"Pune\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenLatitudeIsMissing() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(post("/city").content("{\"cityName\":\"Pune\",\"longitude\":\"73.856255\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenLongitudeIsInvalid() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(post("/city")
					.content("{\"latitude\":\"18.516726\",\"longitude\":\"273.856255\",\"cityName\":\"Pune\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenLatitudeIsInvalid() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(post("/city")
					.content("{\"latitude\":\"118.516726\",\"longitude\":\"73.856255\",\"cityName\":\"Pune\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnCreatedWhenRequestIsValid() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			NewCityRequest request = NewCityRequest.builder().cityName("Pune").latitude("18.516726")
					.longitude("73.856255").build();
			doNothing().when(cityService).addCity(request);
			mockMvc.perform(post("/city")
					.content("{\"latitude\":\"18.516726\",\"longitude\":\"73.856255\",\"cityName\":\"Pune\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

			verify(cityService, times(1)).addCity(request);
		}
	}

	@Nested
	class UpdateCityCoordinates {

		@Test
		void shouldReturnBadRequestWhenRequestBodyIsMissing() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(put("/city/pune")).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenLongitudeIsMissing() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(
					put("/city/pune").content("{\"latitude\":\"18.516726\"}").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenLatitudeIsMissing() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(
					put("/city/pune").content("{\"longitude\":\"73.856255\"}").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenLongitudeIsInvalid() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(put("/city/pune").content("{\"latitude\":\"18.516726\",\"longitude\":\"273.856255\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnBadRequestWhenLatitudeIsInvalid() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			mockMvc.perform(put("/city/pune").content("{\"latitude\":\"118.516726\",\"longitude\":\"73.856255\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturnNoContentWhenRequestIsValid() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			UpdateCityRequest request = UpdateCityRequest.builder().latitude("18.516726").longitude("73.856255")
					.build();
			doNothing().when(cityService).updateCityCoordinates("pune", request);
			mockMvc.perform(put("/city/pune").content("{\"latitude\":\"18.516726\",\"longitude\":\"73.856255\"}")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

			verify(cityService, times(1)).updateCityCoordinates("pune", request);
		}
	}

	@Nested
	class DeleteCityCoordinates {

		@Test
		void shouldReturnNoContentWhenDeletionIsSuccessful() throws Exception {
			MockMvc mockMvc = standaloneSetup(new CityController(cityService)).build();
			doNothing().when(cityService).deleteCityCoordinates("pune");

			mockMvc.perform(delete("/city/pune")).andExpect(status().isNoContent());

			verify(cityService, times(1)).deleteCityCoordinates("pune");
		}
	}

}