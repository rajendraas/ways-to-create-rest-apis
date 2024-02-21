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

type sunriseSunsetClient struct {
	httpClient *http.Client
}

const (
	sunriseSunsetUrl = "https://api.sunrise-sunset.org/json?lat=%s&lng=%s&tzid=Asia/Kolkata"
)

func NewSunriseSunsetClient(httpClient *http.Client) SunriseSunsetClient {
	return &sunriseSunsetClient{httpClient: httpClient}
}

func (client *sunriseSunsetClient) GetSunriseSunsetData(latitude string, longitude string) any {
	url := fmt.Sprintf(sunriseSunsetUrl, latitude, longitude)
	request, _ := http.NewRequest(http.MethodGet, url, nil)
	response, err := client.httpClient.Do(request)
	if err != nil || response.StatusCode != http.StatusOK {
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
