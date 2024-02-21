package router_test

import (
	"net/http"
	"net/http/httptest"
	"testing"

	. "github.com/onsi/ginkgo/v2"
	. "github.com/onsi/gomega"
	mock "github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/mocks"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/router"
	"go.uber.org/mock/gomock"
)

func TestRouter(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Router Suite")
}

var _ = Describe("Router Test", func() {
	var (
		mockController     *gomock.Controller
		cityControllerMock *mock.MockController
	)

	BeforeEach(func() {
		mockController = gomock.NewController(GinkgoT())
		cityControllerMock = mock.NewMockController(mockController)
	})
	Context("City endpoint", func() {
		It("should call handler function when API is called", func() {
			cityControllerMock.EXPECT().CityInfoHandler(gomock.Any()).Times(1)
			appRouter := router.SetupRouter(cityControllerMock)
			req, _ := http.NewRequest("GET", "/city/pune", nil)
			testHttp := httptest.NewRecorder()
			appRouter.ServeHTTP(testHttp, req)
			Expect(testHttp.Result().StatusCode).To(Equal(http.StatusOK))
		})
	})

	Context("Invalid endpoint", func() {
		It("should return Not found if invalid API is called", func() {
			cityControllerMock.EXPECT().CityInfoHandler(gomock.Any()).Times(0)
			appRouter := router.SetupRouter(cityControllerMock)
			req, _ := http.NewRequest("GET", "/city", nil)
			testHttp := httptest.NewRecorder()
			appRouter.ServeHTTP(testHttp, req)
			Expect(testHttp.Result().StatusCode).To(Equal(http.StatusNotFound))
		})
	})
})
