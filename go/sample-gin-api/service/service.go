package service

import (
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/client"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/repository"
)

type Service interface {
	GetCityInfo(cityName string) model.Response
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
	city, err := s.repository.FindCity(cityName)
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
