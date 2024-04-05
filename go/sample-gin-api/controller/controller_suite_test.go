package controller_test

import (
	"bytes"
	"encoding/json"
	"errors"
	"io"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/gin-gonic/gin"
	. "github.com/onsi/ginkgo/v2"
	. "github.com/onsi/gomega"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/controller"
	customerrors "github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/errors"
	mock "github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/mocks"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"go.uber.org/mock/gomock"
)

func TestController(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Controller Suite")
}

var _ = Describe("Controller Test", func() {
	var (
		mockController *gomock.Controller
		cityService    *mock.MockService
		cityController controller.Controller
	)

	BeforeEach(func() {
		mockController = gomock.NewController(GinkgoT())
		cityService = mock.NewMockService(mockController)
		cityController = controller.New(cityService)
	})
	Context("Get city info test", func() {
		It("should return city info", func() {
			var sunriseSunsetResponse any
			var weatherResponse any
			json.Unmarshal([]byte(mock.SunriseSunsetResponse), &sunriseSunsetResponse)
			json.Unmarshal([]byte(mock.WeatherResponse), &weatherResponse)
			cityInfo := model.Response{
				CityName:         "PUNE",
				Latitude:         "18.516726",
				Longitude:        "73.856255",
				Weather:          weatherResponse,
				SunriseAndSunset: sunriseSunsetResponse,
			}
			cityService.EXPECT().GetCityInfo("pune").Times(1).Return(cityInfo)

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Params = []gin.Param{{Key: "cityName", Value: "pune"}}

			cityController.CityInfoHandler(context)

			responseBody, _ := io.ReadAll(testHttp.Result().Body)
			var responseObject model.Response
			json.Unmarshal(responseBody, &responseObject)

			Expect(testHttp.Result().StatusCode).To(Equal(http.StatusOK))

			Expect(responseObject).To(Equal(cityInfo))
		})
	})
	Context("Add city coordinates test", func() {
		It("should return HTTP status 204 if city coordinates are added successfully", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(1).Return(nil)

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := model.AddCityRequest{
				CityName:  "PUNE",
				Latitude:  "18.516726",
				Longitude: "73.856255",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusNoContent))
		})

		It("should return HTTP conflict status if city already exists", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(1).Return(customerrors.NewCityExistsError())

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := &model.AddCityRequest{
				CityName:  "PUNE",
				Latitude:  "18.516726",
				Longitude: "73.856255",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusConflict))
		})

		It("should return HTTP bad request status if request body is not present", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(0)

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusBadRequest))
		})

		It("should return HTTP bad request status if city is not present in request body", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(0)

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := model.AddCityRequest{
				Latitude:  "18.516726",
				Longitude: "73.856255",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusBadRequest))
		})

		It("should return HTTP bad request status if Latitude is not present in request body", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(0)

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := &model.AddCityRequest{
				CityName:  "PUNE",
				Longitude: "73.856255",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusBadRequest))
		})

		It("should return HTTP bad request status if Longitude is not present in request body", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(0)

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := &model.AddCityRequest{
				CityName: "PUNE",
				Latitude: "18.516726",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusBadRequest))
		})

		It("should return HTTP bad request status if Latitude is not in proper format", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(0)

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := model.AddCityRequest{
				CityName:  "PUNE",
				Latitude:  "118.516726",
				Longitude: "73.856255",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusBadRequest))
		})

		It("should return HTTP bad request status if Longitude is not in proper format", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(0)

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := model.AddCityRequest{
				CityName:  "PUNE",
				Latitude:  "18.516726",
				Longitude: "273.856255",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusBadRequest))
		})

		It("should return internal server error if there is repository error", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(1).Return(customerrors.NewRepositoryError())

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := &model.AddCityRequest{
				CityName:  "PUNE",
				Latitude:  "18.516726",
				Longitude: "73.856255",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusInternalServerError))
		})

		It("should return internal server error if there is unknown error", func() {
			cityService.EXPECT().AddCityCoordinates(gomock.Any()).Times(1).Return(errors.New("unknown error"))

			gin.SetMode(gin.TestMode)
			testHttp := httptest.NewRecorder()
			context, _ := gin.CreateTestContext(testHttp)

			context.Request = &http.Request{
				Header: make(http.Header),
			}

			request := &model.AddCityRequest{
				CityName:  "PUNE",
				Latitude:  "18.516726",
				Longitude: "73.856255",
			}

			jsonbytes, err := json.Marshal(request)
			if err != nil {
				panic(err)
			}

			context.Request.Body = io.NopCloser(bytes.NewBuffer(jsonbytes))

			cityController.AddCityHandler(context)

			Expect(context.Writer.Status()).To(Equal(http.StatusInternalServerError))
		})
	})
})
