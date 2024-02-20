package client

import (
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
)

type SunriseSunsetClient interface {
	GetSunriseSunsetData(latitude string, longitude string) any
}

const (
	sunriseSunsetUrl = "https://api.sunrise-sunset.org/json?lat=%s&lng=%s&tzid=Asia/Kolkata"
)

func GetSunriseSunsetData(latitude string, longitude string) any {
	url := fmt.Sprintf(sunriseSunsetUrl, latitude, longitude)
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
