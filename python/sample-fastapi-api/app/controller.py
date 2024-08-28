from . import service


class CityController:
    city_service: service.CityService

    def __init__(self, city_service):
        self.city_service = city_service

    async def get_city_by_name(self, city_name: str):
        return await self.city_service.get_city_by_name(city_name)
