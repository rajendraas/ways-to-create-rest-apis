package model

type Response struct {
	CityName         string `json:"city"`
	Latitude         string `json:"latitude"`
	Longitude        string `json:"longitude"`
	Weather          any    `json:"weather"`
	SunriseAndSunset any    `json:"sunriseAndSunset"`
}
