package repository

import (
	"log"
	"os"

	mysqlDriver "github.com/go-sql-driver/mysql"
	"github.com/joho/godotenv"
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type Repository interface {
	FindCity(cityName string) (*model.City, error)
	CloseDBConnection()
}

type repository struct {
	connection *gorm.DB
}

func New() Repository {
	// loadEnv()

	err := godotenv.Load(".env")

	if err != nil {
		log.Printf("Error loading .env file. Will continue if environment variables are present.")
	}

	db, err := gorm.Open(mysql.New(mysql.Config{
		DSNConfig: &mysqlDriver.Config{
			User:   os.Getenv("MYSQLDB_USER"),
			Passwd: os.Getenv("MYSQLDB_ROOT_PASSWORD"),
			Net:    "tcp",
			Addr:   os.Getenv("MYSQLDB_HOST"),
			DBName: os.Getenv("MYSQLDB_DATABASE"),
		},
	}), &gorm.Config{})
	if err != nil {
		panic("Failed to connect to database")
	}

	log.Printf("Database connection established.")
	return &repository{connection: db}
}

func (db *repository) CloseDBConnection() {
	sqlDB, dbErr := db.connection.DB()
	if dbErr != nil {
		log.Fatalf(dbErr.Error())
	}
	err := sqlDB.Close()
	if err != nil {
		log.Fatalf(err.Error())
	}
	log.Printf("Database connection closed.")
}

func (db *repository) FindCity(cityName string) (*model.City, error) {
	var city model.City
	result := db.connection.First(&city, "city = ?", cityName)
	if result.Error != nil {
		return nil, result.Error
	}

	return &city, nil
}
