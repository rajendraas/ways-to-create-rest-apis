package controller_test

import (
	"encoding/json"
	"io"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/gin-gonic/gin"
	. "github.com/onsi/ginkgo/v2"
	. "github.com/onsi/gomega"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/controller"
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
	Context("Controller Test", func() {
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

			context.Params = []gin.Param{gin.Param{Key: "cityName", Value: "pune"}}

			cityController.CityInfoHandler(context)

			responseBody, _ := io.ReadAll(testHttp.Result().Body)
			var responseObject model.Response
			json.Unmarshal(responseBody, &responseObject)

			Expect(testHttp.Result().StatusCode).To(Equal(http.StatusOK))

			Expect(responseObject).To(Equal(cityInfo))
		})
	})
})
