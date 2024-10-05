from app import repository
from app.database import Base
from .sqllite_db import TestingSessionLocal, engine, insert_pune_city_statement

Base.metadata.create_all(bind=engine)


def test_city_present():
    with TestingSessionLocal() as db:
        db.execute(insert_pune_city_statement)

        city_repository = repository.CityRepository(db)

        result = city_repository.get_city_by_name('PUNE')

        assert result.city == 'PUNE'
        assert result.lat == '18.516726'
        assert result.lng == '73.856255'


def test_city_absent():
    with TestingSessionLocal() as db:
        db.execute(insert_pune_city_statement)

        city_repository = repository.CityRepository(db)

        result = city_repository.get_city_by_name('MUMBAI')

        assert result is None
