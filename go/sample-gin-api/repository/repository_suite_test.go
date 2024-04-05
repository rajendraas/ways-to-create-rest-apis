package repository_test

import (
	"log"
	"testing"

	"github.com/DATA-DOG/go-sqlmock"
	. "github.com/onsi/ginkgo/v2"
	. "github.com/onsi/gomega"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/repository"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

func TestRepository(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Repository Suite")
}

func NewMockDB() (*gorm.DB, sqlmock.Sqlmock) {
	db, mock, err := sqlmock.New()
	if err != nil {
		log.Fatalf("An error '%s' was not expected when opening a stub database connection", err)
	}

	gormDB, err := gorm.Open(mysql.New(mysql.Config{
		Conn:                      db,
		SkipInitializeWithVersion: true,
	}), &gorm.Config{})

	if err != nil {
		log.Fatalf("An error '%s' was not expected when opening gorm database", err)
	}

	return gormDB, mock
}

var _ = Describe("Repository Test", func() {
	var (
		db, mock       = NewMockDB()
		cityRepository repository.Repository
		mockSQL        sqlmock.Sqlmock
	)

	BeforeEach(func() {
		cityRepository = repository.New(db)
		mockSQL = mock
	})

	Context("DB Connection is established", func() {
		It("should return city coordinates if present", func() {
			rows := sqlmock.NewRows([]string{"city", "lat", "lng"}).
				AddRow("PUNE", "18.516726", "73.856255")

			mockSQL.ExpectQuery("^SELECT (.+) FROM `cities` WHERE city =(.+)$").WillReturnRows(rows)

			city, _ := cityRepository.FindCity("pune")

			Expect(city).To(Equal(&model.City{
				CityName:  "PUNE",
				Latitude:  "18.516726",
				Longitude: "73.856255",
			}))
		})

		It("should return error if coordinates are not present", func() {
			rows := sqlmock.NewRows([]string{"city", "lat", "lng"})

			mockSQL.ExpectQuery("^SELECT (.+) FROM `cities` WHERE city =(.+)$").WillReturnRows(rows)

			_, err := cityRepository.FindCity("pune")

			Expect(err).ToNot(BeNil())
		})

		It("should save city coordinates", func() {
			city := model.City{
				CityName:  "BANGALORE",
				Latitude:  "12.971599",
				Longitude: "77.594566",
			}

			mockSQL.ExpectBegin()
			mockSQL.ExpectExec("^INSERT INTO `cities` (.+)$").WithArgs("BANGALORE", "12.971599", "77.594566").WillReturnResult(sqlmock.NewResult(1, 1))
			mockSQL.ExpectCommit()

			err := cityRepository.AddCity(city)

			Expect(err).To(BeNil())
		})
	})
})
