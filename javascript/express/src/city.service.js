import { getCityCoordinates } from "./city.repository.js";
import { getWeather } from "./openmeteo.client.js";
import { getSunriseSunsetTimes } from "./sunrisesunset.client.js";

export const getCityInfo = async (cityName) => {
  const citCoordinates = await getCityCoordinates(cityName);
  if (!citCoordinates) {
    return "City coordinates not found";
  }
  const latitude = citCoordinates.lat;
  const longitude = citCoordinates.lng;
  const weather = await getWeather(latitude, longitude);
  const sunriseAndSunset = await getSunriseSunsetTimes(latitude, longitude);
  return { ...citCoordinates, weather, sunriseAndSunset };
};
