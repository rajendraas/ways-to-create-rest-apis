package client

import (
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
)

type OpenMeteoClient interface {
	GetWeatherData(latitude string, longitude string) any
}

const (
	openmeteoUrl = "https://api.open-meteo.com/v1/forecast?current=temperature_2m,wind_speed_10m&latitude=%s&longitude=%s&timezone=IST"
)

func GetWeatherData(latitude string, longitude string) any {
	url := fmt.Sprintf(openmeteoUrl, latitude, longitude)
	response, err := http.Get(url)
	if err != nil {
		return nil
	}
	var parsedResponse any
	responseBody, byteReadErr := io.ReadAll(response.Body)
	if byteReadErr != nil {
		log.Printf("Error parsing raw weather response")
		return nil
	}
	parsingErr := json.Unmarshal(responseBody, &parsedResponse)
	if parsingErr != nil {
		log.Printf("Error parsing weather response object")
		return nil
	}
	return parsedResponse
}
