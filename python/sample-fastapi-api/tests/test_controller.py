from http.client import HTTPException
from unittest.mock import AsyncMock

import pytest
from app import controller, dtos, service


@pytest.mark.asyncio
async def test_service_called():
    """Test if service function is called"""
    mock_service = AsyncMock(spec=service.CityService)
    city_controller = controller.CityController(mock_service)

    await city_controller.get_city_by_name('pune')

    mock_service.get_city_by_name.assert_called_with('pune')


@pytest.mark.asyncio
async def test_data_return():
    """Test if data is returned"""
    mock_data = dtos.City("PUNE", "18.516726", "73.856255")
    mock_service = AsyncMock(spec=service.CityService)
    mock_service.get_city_by_name.return_value = mock_data
    city_controller = controller.CityController(mock_service)

    response = await city_controller.get_city_by_name('pune')

    assert response == mock_data


@pytest.mark.asyncio
async def test_exception_return():
    """Test if exception is thrown"""
    mock_service = AsyncMock(spec=service.CityService)
    mock_service.get_city_by_name.side_effect = HTTPException()
    city_controller = controller.CityController(mock_service)

    with pytest.raises(HTTPException):
        await city_controller.get_city_by_name('pune')
