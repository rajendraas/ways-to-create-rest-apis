from sqlalchemy.orm import Session
from typing import Type
from . import models


def get_city_by_name(db: Session, city_name: str) -> Type[models.City] | None:
    return db.query(models.City).filter(models.City.city == city_name).first()
