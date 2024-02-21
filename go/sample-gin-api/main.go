package main

import (
	"net/http"

	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/client"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/controller"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/repository"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/router"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/service"
)

var (
	httpClient          http.Client                = http.Client{}
	openMeteoClient     client.OpenMeteoClient     = client.NewOpenMeteoClient(&httpClient)
	sunriseSunsetClient client.SunriseSunsetClient = client.NewSunriseSunsetClient(&httpClient)
	dbConnection        repository.DBConnection    = repository.NewMySqlDbConnection()
	cityRepository      repository.Repository      = repository.New(dbConnection.GetDBConnection())
	cityService         service.Service            = service.New(cityRepository, openMeteoClient, sunriseSunsetClient)
	cityController      controller.Controller      = controller.New(cityService)
)

func main() {
	defer dbConnection.CloseDBConnection()

	service := router.SetupRouter(cityController)

	service.Run()
}
