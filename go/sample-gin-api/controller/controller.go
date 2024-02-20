package controller

import (
	"github.com/gin-gonic/gin"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/service"
)

type Controller interface {
	CityInfoHandler(ctx *gin.Context)
}

type controller struct {
	service service.Service
}

func New(service service.Service) Controller {
	return &controller{service: service}
}

func (c *controller) CityInfoHandler(ctx *gin.Context) {
	ctx.JSON(200, c.getCityInfo(ctx.Param("cityName")))
}

func (c *controller) getCityInfo(cityName string) model.Response {
	return c.service.GetCityInfo(cityName)
}
