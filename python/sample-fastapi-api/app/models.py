from .database import Base
from sqlalchemy import Column, String


class City(Base):
    __tablename__ = 'cities'

    city = Column(String, primary_key=True)
    lat = Column(String)
    lng = Column(String)
