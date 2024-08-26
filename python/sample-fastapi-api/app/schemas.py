from pydantic import BaseModel
from typing import Optional, Any


class City(BaseModel):
    city: str
    lat: str
    lng: str
    weather: Optional[dict] = {}
    sunriseAndSunset: Optional[dict] = {}

    def __init__(self, city, lat, lng, weather, sunriseAndSunset):
        self.city = city
        self.lat = lat
        self.lng = lng
        self.weather = weather
        self.sunriseAndSunset = sunriseAndSunset
