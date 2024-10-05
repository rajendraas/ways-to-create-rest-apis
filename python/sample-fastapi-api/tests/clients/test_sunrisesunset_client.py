from unittest.mock import patch

import pytest
from httpx import Response

from app.clients.sunrisesunset_client import get_sunrise_sunset_async

sunrisesunset_response = {
    "results": {
        "sunrise": "7:06:23 AM",
        "sunset": "6:30:11 PM",
        "solar_noon": "12:48:17 PM",
        "day_length": "11:23:48",
        "civil_twilight_begin": "6:44:51 AM",
        "civil_twilight_end": "6:51:42 PM",
        "nautical_twilight_begin": "6:18:42 AM",
        "nautical_twilight_end": "7:17:52 PM",
        "astronomical_twilight_begin": "5:52:44 AM",
        "astronomical_twilight_end": "7:43:50 PM"
    },
    "status": "OK",
    "tzid": "Asia/Kolkata"
}


@patch('httpx.AsyncClient.get')
@pytest.mark.anyio
async def test_valid_sunrisesunset_response(httpx_mock):
    httpx_mock.return_value = Response(200, json=sunrisesunset_response)

    response = await get_sunrise_sunset_async('18.516726', '73.856255')
    assert response == sunrisesunset_response


@patch('httpx.AsyncClient.get')
@pytest.mark.anyio
async def test_api_failure(httpx_mock):
    httpx_mock.return_value = Response(500, json={"error": "Internal server error"})

    response = await get_sunrise_sunset_async('18.516726', '73.856255')
    assert response == {}
