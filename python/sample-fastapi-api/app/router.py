from fastapi import APIRouter, status

from . import dependencies, schemas

city_router = APIRouter(prefix="/city")


@city_router.get("/{city_name}", response_model=schemas.City, status_code=status.HTTP_200_OK)
async def get_city_by_name(city_controller: dependencies.city_controller_dependency, city_name: str):
    return await city_controller.get_city_by_name(city_name)
