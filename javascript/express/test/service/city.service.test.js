import { getCityInfo } from "../../src/service/city.service.js";
import * as cityRepository from "../../src/repository/city.repository.js";
import * as openmeteoClient from "../../src/client/openmeteo.client.js";
import * as sunrisesunsetClient from "../../src/client/sunrisesunset.client.js";
import { sunriseAndSunsetResponse } from "../responseData/sunriseAndSunsetResponse.js";
import { weatherResponse } from "../responseData/weatherResponse.js";
import { cityApiResponse } from "../responseData/cityApiResponse.js";

describe("City service test", () => {
  it("should return city info when all details are present", async () => {
    jest.spyOn(cityRepository, "getCityCoordinates").mockReturnValue(
      Promise.resolve({
        city: "PUNE",
        lat: "18.516726",
        lng: "73.856255",
      })
    );
    jest
      .spyOn(openmeteoClient, "getWeather")
      .mockReturnValue(Promise.resolve(weatherResponse));
    jest
      .spyOn(sunrisesunsetClient, "getSunriseSunsetTimes")
      .mockReturnValue(Promise.resolve(sunriseAndSunsetResponse));

    const response = await getCityInfo("pune");

    expect(response).toStrictEqual(cityApiResponse);
  });

  it("should return error if city coordinates are not found", async () => {
    jest
      .spyOn(cityRepository, "getCityCoordinates")
      .mockReturnValue(Promise.resolve(undefined));

    const response = await getCityInfo("pune");

    expect(response).toBe("City coordinates not found");
  });

  it("should return no weather or sunrisesunset details if there is no data", async () => {
    jest.spyOn(cityRepository, "getCityCoordinates").mockReturnValue(
      Promise.resolve({
        city: "PUNE",
        lat: "18.516726",
        lng: "73.856255",
      })
    );
    jest
      .spyOn(openmeteoClient, "getWeather")
      .mockReturnValue(Promise.resolve(undefined));
    jest
      .spyOn(sunrisesunsetClient, "getSunriseSunsetTimes")
      .mockReturnValue(Promise.resolve(undefined));

    const response = await getCityInfo("pune");

    expect(response).toStrictEqual({
      city: "PUNE",
      lat: "18.516726",
      lng: "73.856255",
      sunriseAndSunset: undefined,
      weather: undefined,
    });
  });
});
