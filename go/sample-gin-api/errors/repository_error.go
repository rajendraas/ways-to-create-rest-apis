package customerrors

import (
	"errors"
	"net/http"
)

type RepositoryError struct {
	StatusCode int
	Err        error
}

func (c RepositoryError) Error() string {
	return c.Err.Error()
}

func NewRepositoryError() RepositoryError {
	return RepositoryError{
		StatusCode: http.StatusConflict,
		Err:        errors.New("city already exists"),
	}
}
