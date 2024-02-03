import axios from "axios";
import { sunriseAndSunsetResponse } from "../responseData/sunriseAndSunsetResponse.js";
import { getSunriseSunsetTimes } from "../../src/client/sunrisesunset.client";

jest.mock("axios");

describe("SunriseSunset client test", () => {
  it("should return sunrise & sunset data on successful API call", async () => {
    axios.get.mockImplementation(() =>
      Promise.resolve({ data: sunriseAndSunsetResponse })
    );

    const actualSunriseAndSunsetResponse = await getSunriseSunsetTimes(
      "18.516726",
      "73.856255"
    );

    expect(axios.get).toHaveBeenCalledWith(
      "https://api.sunrise-sunset.org/json?tzid=Asia/Kolkata",
      { params: { lat: "18.516726", lng: "73.856255" } }
    );
    expect(actualSunriseAndSunsetResponse).toStrictEqual(
      sunriseAndSunsetResponse
    );
  });

  it("should return undefined when SunriseSunset API fails", async () => {
    axios.get.mockImplementation(() => Promise.reject(new Error()));

    const actualSunriseAndSunsetResponse = await getSunriseSunsetTimes(
      "18.516726",
      "73.856255"
    );

    expect(actualSunriseAndSunsetResponse).toBeUndefined();
  });
});
