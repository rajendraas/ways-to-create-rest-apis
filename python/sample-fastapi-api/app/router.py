from fastapi import APIRouter, status

from . import dependencies, schemas, service

city_router = APIRouter(prefix="/city")


@city_router.get("/{city_name}", response_model=schemas.City, status_code=status.HTTP_200_OK)
async def get_city_by_name(db: dependencies.db_dependency, city_name: str):
    return await service.get_city_by_name(db, city_name)
