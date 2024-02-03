import axios from "axios";
import { weatherResponse } from "../responseData/weatherResponse.js";
import { getWeather } from "../../src/client/openmeteo.client";

jest.mock("axios");

describe("OpenMeteo client test", () => {
  it("should return weather data on successful API call", async () => {
    axios.get.mockImplementation(() =>
      Promise.resolve({ data: weatherResponse })
    );

    const actualWeatherResponse = await getWeather("18.516726", "73.856255");

    expect(axios.get).toHaveBeenCalledWith(
      "https://api.open-meteo.com/v1/forecast?current=temperature_2m,wind_speed_10m&timezone=IST",
      { params: { latitude: "18.516726", longitude: "73.856255" } }
    );
    expect(actualWeatherResponse).toStrictEqual(weatherResponse);
  });

  it("should return undefined when weather API fails", async () => {
    axios.get.mockImplementation(() => Promise.reject(new Error()));

    const actualWeatherResponse = await getWeather("18.516726", "73.856255");

    expect(actualWeatherResponse).toBeUndefined();
  });
});
