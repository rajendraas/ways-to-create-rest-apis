from typing import Annotated

from fastapi import Depends
from sqlalchemy.orm import Session

from . import service, controller, repository
from .database import SessionLocal


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


def create_repository_instance(db: Annotated[Session, Depends(get_db)]):
    return repository.CityRepository(db)


def create_service_instance(city_repository: Annotated[repository.CityRepository, Depends(create_repository_instance)]):
    return service.CityService(city_repository)


def create_controller_instance(city_service: Annotated[service.CityService, Depends(create_service_instance)]):
    return controller.CityController(city_service)


city_controller_dependency = Annotated[controller.CityController, Depends(create_controller_instance)]
