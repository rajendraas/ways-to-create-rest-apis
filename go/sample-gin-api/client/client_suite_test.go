package client_test

import (
	"encoding/json"
	"net/http"
	"testing"

	"github.com/h2non/gock"
	. "github.com/onsi/ginkgo/v2"
	. "github.com/onsi/gomega"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/client"
	mock "github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/mocks"
)

func TestClient(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Client Suite")
}

var _ = Describe("OpenMeteoClient Test", func() {

	var (
		openMeteoClient client.OpenMeteoClient
	)

	BeforeEach(func() {
		openMeteoClient = client.NewOpenMeteoClient(&http.Client{})
	})

	Context("Successful call", func() {
		It("should return the weather data on successful API call", func() {
			defer gock.Off()

			var weatherResponse any
			json.Unmarshal([]byte(mock.WeatherResponse), &weatherResponse)

			gock.New("https://api.open-meteo.com").
				MatchParams(map[string]string{
					"current":   "temperature_2m,wind_speed_10m",
					"latitude":  "18.516726",
					"longitude": "73.856255",
					"timezone":  "IST",
				}).
				Get("/v1/forecast").
				Reply(http.StatusOK).
				JSON(weatherResponse)

			response := openMeteoClient.GetWeatherData("18.516726", "73.856255")

			Expect(response).To(Equal(weatherResponse))
		})
	})

	Context("Failed call", func() {
		It("should return nil on API failure", func() {
			defer gock.Off()

			gock.New("https://api.open-meteo.com").
				MatchParams(map[string]string{
					"current":   "temperature_2m,wind_speed_10m",
					"latitude":  "18.516726",
					"longitude": "73.856255",
					"timezone":  "IST",
				}).
				Get("/v1/forecast").
				Reply(http.StatusInternalServerError)

			response := openMeteoClient.GetWeatherData("18.516726", "73.856255")

			Expect(response).To(BeNil())
		})

		It("should return nil on invalid data", func() {
			defer gock.Off()

			gock.New("https://api.open-meteo.com").
				MatchParams(map[string]string{
					"current":   "temperature_2m,wind_speed_10m",
					"latitude":  "18.516726",
					"longitude": "73.856255",
					"timezone":  "IST",
				}).
				Get("/v1/forecast").
				Reply(http.StatusOK).
				JSON([]byte{})

			response := openMeteoClient.GetWeatherData("18.516726", "73.856255")

			Expect(response).To(BeNil())
		})
	})
})

var _ = Describe("SunriseSunsetClient Test", func() {

	var (
		sunriseSunsetClient client.SunriseSunsetClient
	)

	BeforeEach(func() {
		sunriseSunsetClient = client.NewSunriseSunsetClient(&http.Client{})
	})

	Context("Successful call", func() {
		It("should return the weather data on successful API call", func() {
			defer gock.Off()

			var sunriseSunsetResponse any
			json.Unmarshal([]byte(mock.SunriseSunsetResponse), &sunriseSunsetResponse)

			gock.New("https://api.sunrise-sunset.org").
				MatchParams(map[string]string{
					"lat":  "18.516726",
					"lng":  "73.856255",
					"tzid": "Asia/Kolkata",
				}).
				Get("/json").
				Reply(http.StatusOK).
				JSON(sunriseSunsetResponse)

			response := sunriseSunsetClient.GetSunriseSunsetData("18.516726", "73.856255")

			Expect(response).To(Equal(sunriseSunsetResponse))
		})
	})

	Context("Failed call", func() {
		It("should return nil on API failure", func() {
			defer gock.Off()

			gock.New("https://api.sunrise-sunset.org").
				MatchParams(map[string]string{
					"lat":  "18.516726",
					"lng":  "73.856255",
					"tzid": "Asia/Kolkata",
				}).
				Get("/json").
				Reply(http.StatusInternalServerError)

			response := sunriseSunsetClient.GetSunriseSunsetData("18.516726", "73.856255")

			Expect(response).To(BeNil())
		})

		It("should return nil on invalid data", func() {
			defer gock.Off()

			gock.New("https://api.sunrise-sunset.org").
				MatchParams(map[string]string{
					"lat":  "18.516726",
					"lng":  "73.856255",
					"tzid": "Asia/Kolkata",
				}).
				Get("/json").
				Reply(http.StatusOK).
				JSON([]byte{})

			response := sunriseSunsetClient.GetSunriseSunsetData("18.516726", "73.856255")

			Expect(response).To(BeNil())
		})
	})
})
