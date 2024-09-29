import httpx

URL = 'https://api.open-meteo.com/v1/forecast?current=temperature_2m,wind_speed_10m&timezone=IST'


async def get_weather_async(latitude: str, longitude: str):
    params = {'latitude': latitude, 'longitude': longitude}
    async with httpx.AsyncClient() as client:
        response = await client.get(URL, params=params)
        return response.json() if response.status_code == 200 else {}
