package model

type AddCityRequest struct {
	CityName  string `json:"city" binding:"required"`
	Latitude  string `json:"latitude" binding:"required,latitude"`
	Longitude string `json:"longitude" binding:"required,longitude"`
}
