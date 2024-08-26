from typing import Optional, Any


class City:
    city: str
    lat: str
    lng: str
    weather: Optional[dict] = {}
    sunriseAndSunset: Optional[dict] = {}

    def __init__(self, city, lat, lng):
        self.city = city
        self.lat = lat
        self.lng = lng

    def set_weather(self, weather: dict):
        self.weather = weather

    def set_sunrise_sunset(self, sunrise_sunset: dict):
        self.sunriseAndSunset = sunrise_sunset
