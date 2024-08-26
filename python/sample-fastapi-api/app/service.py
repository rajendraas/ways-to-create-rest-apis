import asyncio

from fastapi import HTTPException, status
from sqlalchemy.orm import Session

from . import repository, dtos
from .clients import openmeteo_client, sunrisesunset_client


async def get_city_by_name(db: Session, city_name: str):
    city_data = repository.get_city_by_name(db, city_name)
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
