import httpx

URL = 'https://api.sunrise-sunset.org/json?tzid=Asia/Kolkata'


async def get_sunrise_sunset_async(latitude: str, longitude: str):
    params = {'lat': latitude, 'lng': longitude}
    async with httpx.AsyncClient() as client:
        response = await client.get(URL, params=params)
        return response.json() if response.status_code == 200 else {}
