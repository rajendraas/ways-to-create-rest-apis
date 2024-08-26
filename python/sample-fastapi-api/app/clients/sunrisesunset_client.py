import httpx


URL = 'https://api.sunrise-sunset.org/json?tzid=Asia/Kolkata'


async def get_sunrise_sunset_async(latitude: str, longitude: str, api_response: dict):
    params = {'lat': latitude, 'lng': longitude}
    async with httpx.AsyncClient() as client:
        response = await client.get(URL, params=params)
        api_response['sunrise_sunset'] = response.json() if response.status_code == 200 else {}
