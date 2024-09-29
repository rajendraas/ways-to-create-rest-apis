from unittest.mock import patch

import pytest
from httpx import ASGITransport, AsyncClient
from sqlalchemy import create_engine, text
from sqlalchemy.orm import sessionmaker
from sqlalchemy.pool import StaticPool

from app.database import Base
from app.dependencies import get_db
from app.main import app

SQLALCHEMY_DATABASE_URL = "sqlite://"

engine = create_engine(
    SQLALCHEMY_DATABASE_URL,
    connect_args={"check_same_thread": False},
    poolclass=StaticPool,
)
TestingSessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base.metadata.create_all(bind=engine)


def override_get_db():
    try:
        db = TestingSessionLocal()
        db.execute(
            text(
                "INSERT INTO cities(city,lat,lng) SELECT 'PUNE','18.516726','73.856255' WHERE NOT EXISTS(SELECT 1 FROM cities WHERE city = 'PUNE')"))

        yield db
    finally:
        db.close()


app.dependency_overrides[get_db] = override_get_db


@pytest.fixture
def anyio_backend():
    return 'asyncio'


@pytest.mark.anyio
async def test_invalid_url():
    async with AsyncClient(
            transport=ASGITransport(app=app), base_url="http://test"
    ) as ac:
        response = await ac.get("/something")
    assert response.status_code == 404


@pytest.mark.anyio
async def test_health_api():
    async with AsyncClient(
            transport=ASGITransport(app=app), base_url="http://test"
    ) as ac:
        response = await ac.get("/health")
    assert response.status_code == 200
    assert response.json() == {"status": "healthy"}


@pytest.mark.anyio
async def test_city_api_city_absent():
    async with AsyncClient(
            transport=ASGITransport(app=app), base_url="http://test"
    ) as ac:
        response = await ac.get("/city/mumbai")
    assert response.status_code == 400
    assert response.json() == {'detail': 'City not found'}


@patch('app.clients.openmeteo_client.get_weather_async')
@patch('app.clients.sunrisesunset_client.get_sunrise_sunset_async')
@pytest.mark.anyio
async def test_city_api_city_present(get_weather_async_mock, get_sunrise_sunset_async_mock):
    get_weather_async_mock.return_value = {}
    get_sunrise_sunset_async_mock.return_value = {}

    async with AsyncClient(transport=ASGITransport(app=app), base_url="http://test") as ac:
        response = await ac.get("/city/pune")
        assert response.status_code == 200
        assert response.json() == {'city': 'PUNE', 'lat': '18.516726', 'lng': '73.856255', 'weather': {},
                                   'sunriseAndSunset': {}}
