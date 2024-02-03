import axios from "axios";

export const getSunriseSunsetTimes = async (latitude, longitude) => {
  try {
    const sunriseAndSunset = await axios.get(
      `https://api.sunrise-sunset.org/json?tzid=Asia/Kolkata`,
      {
        params: {
          lat: latitude,
          lng: longitude,
        },
      }
    );
    return sunriseAndSunset.data;
  } catch (err) {
    console.error(err);
  }
};
