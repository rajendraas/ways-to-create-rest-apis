package controller

import (
	"errors"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	customerrors "github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/errors"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/service"
)

type Controller interface {
	CityInfoHandler(ctx *gin.Context)
	AddCityHandler(ctx *gin.Context)
}

type controller struct {
	service service.Service
}

func New(service service.Service) Controller {
	return &controller{service: service}
}

func (c *controller) CityInfoHandler(ctx *gin.Context) {
	cityName := ctx.Param("cityName")
	cityInfoResponse := c.service.GetCityInfo(cityName)
	ctx.JSON(http.StatusOK, cityInfoResponse)
}

func (c *controller) AddCityHandler(ctx *gin.Context) {
	var request model.AddCityRequest
	if validationError := ctx.ShouldBindJSON(&request); validationError != nil {
		var ve validator.ValidationErrors
		if errors.As(validationError, &ve) {
			out := make([]model.ErrorMessage, len(ve))
			for i, fe := range ve {
				out[i] = model.ErrorMessage{Field: fe.Field(), Message: getValidationErrorMsg(fe)}
			}
			ctx.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"errors": out})
		} else {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"errors": validationError.Error()})
		}
		return
	}
	err := c.service.AddCityCoordinates(request)
	if err != nil {
		if cityExistsErr, ok := err.(customerrors.CityExistsError); ok {
			ctx.AbortWithStatusJSON(http.StatusConflict, gin.H{"errors": cityExistsErr.Error()})
		} else if repositoryErr, ok := err.(customerrors.RepositoryError); ok {
			ctx.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"errors": repositoryErr.Error()})
		} else {
			ctx.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"errors": err.Error()})
		}
		return
	}
	ctx.Status(http.StatusNoContent)
}

func getValidationErrorMsg(fe validator.FieldError) string {
	switch fe.Tag() {
	case "required":
		return "This field is required"
	case "latitude":
		return "Not in proper latitude format"
	case "longitude":
		return "Not in proper longitude format"
	}
	return "Unknown error"
}
