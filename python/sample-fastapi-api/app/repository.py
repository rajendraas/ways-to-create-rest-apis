from typing import Type

from sqlalchemy.orm import Session

from . import models


class CityRepository:
    db: Session

    def __init__(self, db):
        self.db = db

    def get_city_by_name(self, city_name: str) -> Type[models.City] | None:
        return self.db.query(models.City).filter(models.City.city == city_name).first()
