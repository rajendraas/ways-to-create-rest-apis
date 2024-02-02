import axios from "axios";

export const getWeather = async (latitude, longitude) => {
  try {
    const weather = await axios.get(
      `https://api.open-meteo.com/v1/forecast?current=temperature_2m,wind_speed_10m&timezone=IST`,
      {
        params: {
          latitude,
          longitude,
        },
      }
    );
    return weather.data;
  } catch (err) {
    console.error(err);
  }
};
