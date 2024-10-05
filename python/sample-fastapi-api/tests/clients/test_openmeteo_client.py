from unittest.mock import patch

import pytest
from httpx import Response

from app.clients.openmeteo_client import get_weather_async

weather_response = {
    "latitude": 18.625,
    "longitude": 74,
    "generationtime_ms": 0.03707408905029297,
    "utc_offset_seconds": 19800,
    "timezone": "Asia/Calcutta",
    "timezone_abbreviation": "IST",
    "elevation": 562,
    "current_units": {
        "time": "iso8601",
        "interval": "seconds",
        "temperature_2m": "°C",
        "wind_speed_10m": "km/h"
    },
    "current": {
        "time": "2024-02-03T10:00",
        "interval": 900,
        "temperature_2m": 24.2,
        "wind_speed_10m": 7.6
    }
}


@patch('httpx.AsyncClient.get')
@pytest.mark.anyio
async def test_valid_weather_response(httpx_mock):
    httpx_mock.return_value = Response(200, json=weather_response)

    response = await get_weather_async('18.516726', '73.856255')
    assert response == weather_response


@patch('httpx.AsyncClient.get')
@pytest.mark.anyio
async def test_api_failure(httpx_mock):
    httpx_mock.return_value = Response(500, json={"error": "Internal server error"})

    response = await get_weather_async('18.516726', '73.856255')
    assert response == {}
