package repository

import (
	"github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	"gorm.io/gorm"
)

type Repository interface {
	FindCity(cityName string) (*model.City, error)
}

type repository struct {
	connection *gorm.DB
}

func New(connection *gorm.DB) Repository {
	return &repository{connection: connection}
}

func (db *repository) FindCity(cityName string) (*model.City, error) {
	var city model.City
	result := db.connection.First(&city, "city = ?", cityName)
	if result.Error != nil {
		return nil, result.Error
	}

	return &city, nil
}
