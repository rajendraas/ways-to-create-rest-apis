package router

import (
	"github.com/gin-gonic/gin"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/controller"
)

func SetupRouter(cityController controller.Controller) *gin.Engine {
	router := gin.Default()

	router.GET("/city/:cityName", cityController.CityInfoHandler)
	router.POST("/city", cityController.AddCityHandler)
	return router
}
