package model

type City struct {
	CityName  string `json:"city" gorm:"column:city;type:varchar(100);primaryKey"`
	Latitude  string `json:"latitude" gorm:"column:lat;type:varchar(100)"`
	Longitude string `json:"longitude" gorm:"column:lng;type:varchar(100)"`
}
