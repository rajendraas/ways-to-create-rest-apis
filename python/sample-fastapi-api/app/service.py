import asyncio

from fastapi import HTTPException, status

from . import repository, dtos
from .clients import openmeteo_client, sunrisesunset_client


class CityService:
    city_repository: repository.CityRepository

    def __init__(self, city_repository: repository.CityRepository):
        self.city_repository = city_repository

    async def get_city_by_name(self, city_name: str):
        city_data = self.city_repository.get_city_by_name(city_name)
        if city_data is None:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail='City not found')
        city = dtos.City(city_data.city, city_data.lat, city_data.lng)
        api_responses: dict = {}
        await asyncio.gather(
            openmeteo_client.get_weather_async(city.lat, city.lng, api_responses),
            sunrisesunset_client.get_sunrise_sunset_async(city.lat, city.lng, api_responses)
        )
        city.set_weather(api_responses.get('weather'))
        city.set_sunrise_sunset(api_responses.get('sunrise_sunset'))
        return city