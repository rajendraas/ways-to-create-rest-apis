package service

import (
	"strings"

	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/client"
	customerrors "github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/errors"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/repository"
)

type Service interface {
	GetCityInfo(cityName string) model.Response
	AddCityCoordinates(request model.AddCityRequest) error
}

type service struct {
	repository          repository.Repository
	openMeteoClient     client.OpenMeteoClient
	sunriseSunsetClient client.SunriseSunsetClient
}

func New(repository repository.Repository, openMeteoClient client.OpenMeteoClient, sunriseSunsetClient client.SunriseSunsetClient) Service {
	return &service{repository: repository, openMeteoClient: openMeteoClient, sunriseSunsetClient: sunriseSunsetClient}
}

func (s *service) GetCityInfo(cityName string) model.Response {
	city, err := s.repository.FindCity(strings.ToUpper(cityName))
	if err != nil {
		return model.Response{}
	}
	weather := s.openMeteoClient.GetWeatherData(city.Latitude, city.Longitude)
	sunriseAndSunset := s.sunriseSunsetClient.GetSunriseSunsetData(city.Latitude, city.Longitude)
	return model.Response{
		CityName:         city.CityName,
		Latitude:         city.Latitude,
		Longitude:        city.Longitude,
		Weather:          weather,
		SunriseAndSunset: sunriseAndSunset,
	}
}

func (s *service) AddCityCoordinates(request model.AddCityRequest) error {
	_, err := s.repository.FindCity(request.CityName)
	if err == nil {
		return customerrors.NewCityExistsError()
	}
	repositoryErr := s.repository.AddCity(model.City{
		CityName:  strings.ToUpper(request.CityName),
		Latitude:  request.Latitude,
		Longitude: request.Longitude,
	})
	if repositoryErr != nil {
		return customerrors.NewRepositoryError()
	}
	return nil
}
