from sqlalchemy import String
from sqlalchemy.orm import mapped_column, Mapped

from .database import Base


class City(Base):
    __tablename__ = 'cities'

    city: Mapped[String] = mapped_column(String, primary_key=True)
    lat: Mapped[String] = mapped_column(String)
    lng: Mapped[String] = mapped_column(String)
