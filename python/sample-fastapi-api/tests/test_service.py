from unittest.mock import AsyncMock, MagicMock, patch

import pytest
from fastapi import HTTPException

from app import service, models, repository, dtos


@patch('app.clients.openmeteo_client.get_weather_async')
@patch('app.clients.sunrisesunset_client.get_sunrise_sunset_async')
@pytest.mark.asyncio
async def test_repository_and_clients_called(get_weather_async_mock, get_sunrise_sunset_async_mock):
    mock_data = MagicMock(spec=models.City)
    mock_data.city = "PUNE"
    mock_data.lat = "18.516726"
    mock_data.lng = "73.856255"
    mock_repository = AsyncMock(spec=repository.CityRepository)
    mock_repository.get_city_by_name.return_value = mock_data
    city_service = service.CityService(mock_repository)

    await city_service.get_city_by_name('pune')

    mock_repository.get_city_by_name.assert_called_with('pune')
    get_weather_async_mock.assert_called_with("18.516726", "73.856255")
    get_sunrise_sunset_async_mock.assert_called_with("18.516726", "73.856255")


@patch('app.clients.openmeteo_client.get_weather_async')
@patch('app.clients.sunrisesunset_client.get_sunrise_sunset_async')
@pytest.mark.asyncio
async def test_data_is_returned(get_weather_async_mock, get_sunrise_sunset_async_mock):
    mock_response = dtos.City("PUNE", "18.516726", "73.856255")
    mock_response.set_weather({})
    mock_response.set_sunrise_sunset({})
    mock_data = MagicMock(spec=models.City)
    mock_data.city = "PUNE"
    mock_data.lat = "18.516726"
    mock_data.lng = "73.856255"
    get_weather_async_mock.return_value = {}
    get_sunrise_sunset_async_mock.return_value = {}
    mock_repository = AsyncMock(spec=repository.CityRepository)
    mock_repository.get_city_by_name.return_value = mock_data
    city_service = service.CityService(mock_repository)

    response = await city_service.get_city_by_name('pune')

    assert response.__dict__ == mock_response.__dict__


@pytest.mark.asyncio
async def test_clients_not_called():
    mock_repository = AsyncMock(spec=repository.CityRepository)
    mock_repository.get_city_by_name.return_value = None
    city_service = service.CityService(mock_repository)

    with pytest.raises(HTTPException):
        await city_service.get_city_by_name('pune')
