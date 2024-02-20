package main

import (
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/controller"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/repository"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/router"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/service"
)

var (
	cityRepository repository.Repository = repository.New()
	cityService    service.Service       = service.New(cityRepository)
	cityController controller.Controller = controller.New(cityService)
)

func main() {
	defer cityRepository.CloseDBConnection()

	service := router.SetupRouter(cityController)

	service.Run()
}
