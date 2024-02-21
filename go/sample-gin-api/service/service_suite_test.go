package service_test

import (
	"encoding/json"
	"errors"
	"testing"

	. "github.com/onsi/ginkgo/v2"
	. "github.com/onsi/gomega"
	mock "github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/mocks"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/service"
	"go.uber.org/mock/gomock"
)

func TestService(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Service Suite")
}

var _ = Describe("City service", func() {

	var (
		mockController    *gomock.Controller
		repositoryMock    *mock.MockRepository
		cityService       service.Service
		openMeteoMock     *mock.MockOpenMeteoClient
		sunriseSunsetMock *mock.MockSunriseSunsetClient
	)

	BeforeEach(func() {
		mockController = gomock.NewController(GinkgoT())
		repositoryMock = mock.NewMockRepository(mockController)
		openMeteoMock = mock.NewMockOpenMeteoClient(mockController)
		sunriseSunsetMock = mock.NewMockSunriseSunsetClient(mockController)
		cityService = service.New(repositoryMock, openMeteoMock, sunriseSunsetMock)
	})

	Context("City name is not found in table", func() {
		It("should return empty response", func() {
			repositoryMock.EXPECT().FindCity("mumbai").Times(1).Return(nil, errors.New("Not found"))
			openMeteoMock.EXPECT().GetWeatherData(gomock.Any(), gomock.Any()).Times(0)
			sunriseSunsetMock.EXPECT().GetSunriseSunsetData(gomock.Any(), gomock.Any()).Times(0)

			cityInfo := cityService.GetCityInfo("mumbai")

			Expect(cityInfo).To(Equal(model.Response{}))
		},
		)
	})

	Context("City name is present in table", func() {
		It("should return latitude & longitude even if all external API calls fail", func() {
			repositoryMock.EXPECT().FindCity("pune").Times(1).Return(&model.City{CityName: "PUNE", Latitude: "18.516726", Longitude: "73.856255"}, nil)
			openMeteoMock.EXPECT().GetWeatherData("18.516726", "73.856255").Times(1).Return(nil)
			sunriseSunsetMock.EXPECT().GetSunriseSunsetData("18.516726", "73.856255").Times(1).Return(nil)

			cityInfo := cityService.GetCityInfo("pune")

			Expect(cityInfo).To(Equal(model.Response{CityName: "PUNE", Latitude: "18.516726", Longitude: "73.856255"}))
		},
		)

		It("should return latitude, longitude & weather if sunriseSunset API call fails", func() {
			var weatherResponse any
			json.Unmarshal([]byte(mock.WeatherResponse), &weatherResponse)
			repositoryMock.EXPECT().FindCity("pune").Times(1).Return(&model.City{CityName: "PUNE", Latitude: "18.516726", Longitude: "73.856255"}, nil)
			openMeteoMock.EXPECT().GetWeatherData("18.516726", "73.856255").Times(1).Return(weatherResponse)
			sunriseSunsetMock.EXPECT().GetSunriseSunsetData("18.516726", "73.856255").Times(1).Return(nil)

			cityInfo := cityService.GetCityInfo("pune")

			Expect(cityInfo).To(Equal(model.Response{CityName: "PUNE", Latitude: "18.516726", Longitude: "73.856255", Weather: weatherResponse}))
		},
		)

		It("should return latitude, longitude & sunriseSunset if weather API call fails", func() {
			var sunriseSunsetResponse any
			json.Unmarshal([]byte(mock.SunriseSunsetResponse), &sunriseSunsetResponse)
			repositoryMock.EXPECT().FindCity("pune").Times(1).Return(&model.City{CityName: "PUNE", Latitude: "18.516726", Longitude: "73.856255"}, nil)
			openMeteoMock.EXPECT().GetWeatherData("18.516726", "73.856255").Times(1).Return(nil)
			sunriseSunsetMock.EXPECT().GetSunriseSunsetData("18.516726", "73.856255").Times(1).Return(sunriseSunsetResponse)

			cityInfo := cityService.GetCityInfo("pune")

			Expect(cityInfo).To(Equal(model.Response{
				CityName:         "PUNE",
				Latitude:         "18.516726",
				Longitude:        "73.856255",
				SunriseAndSunset: sunriseSunsetResponse,
			}))
		},
		)

		It("should return all data when both API calls are successful", func() {
			var sunriseSunsetResponse any
			var weatherResponse any
			json.Unmarshal([]byte(mock.SunriseSunsetResponse), &sunriseSunsetResponse)
			json.Unmarshal([]byte(mock.WeatherResponse), &weatherResponse)
			repositoryMock.EXPECT().FindCity("pune").Times(1).Return(&model.City{CityName: "PUNE", Latitude: "18.516726", Longitude: "73.856255"}, nil)
			openMeteoMock.EXPECT().GetWeatherData("18.516726", "73.856255").Times(1).Return(weatherResponse)
			sunriseSunsetMock.EXPECT().GetSunriseSunsetData("18.516726", "73.856255").Times(1).Return(sunriseSunsetResponse)

			cityInfo := cityService.GetCityInfo("pune")

			Expect(cityInfo).To(Equal(model.Response{
				CityName:         "PUNE",
				Latitude:         "18.516726",
				Longitude:        "73.856255",
				Weather:          weatherResponse,
				SunriseAndSunset: sunriseSunsetResponse,
			}))
		},
		)
	})
})
