package model

type ErrorMessage struct {
	Field   string `json:"field"`
	Message string `json:"message"`
}
