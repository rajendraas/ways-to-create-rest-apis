from unittest.mock import patch

import pytest
from httpx import ASGITransport, AsyncClient

from app.dependencies import get_db
from app.main import app
from .sqllite_db import sqllite_db

app.dependency_overrides[get_db] = sqllite_db


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
