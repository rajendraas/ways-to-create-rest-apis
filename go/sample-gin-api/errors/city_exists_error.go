package customerrors

import (
	"errors"
	"net/http"
)

type CityExistsError struct {
	StatusCode int
	Err        error
}

func (c CityExistsError) Error() string {
	return c.Err.Error()
}

func NewCityExistsError() CityExistsError {
	return CityExistsError{
		StatusCode: http.StatusConflict,
		Err:        errors.New("city already exists"),
	}
}
