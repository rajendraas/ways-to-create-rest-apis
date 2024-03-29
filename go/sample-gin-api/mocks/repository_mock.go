// Code generated by MockGen. DO NOT EDIT.
// Source: repository/repository.go
//
// Generated by this command:
//
//	mockgen --source=repository/repository.go --destination=mocks/repository_mock.go --package=mock
//

// Package mock is a generated GoMock package.
package mock

import (
	reflect "reflect"

	model "github.com/rajendrasatpute/ways-to-create-rest-apis/go/sample-gin-api/model"
	gomock "go.uber.org/mock/gomock"
)

// MockRepository is a mock of Repository interface.
type MockRepository struct {
	ctrl     *gomock.Controller
	recorder *MockRepositoryMockRecorder
}

// MockRepositoryMockRecorder is the mock recorder for MockRepository.
type MockRepositoryMockRecorder struct {
	mock *MockRepository
}

// NewMockRepository creates a new mock instance.
func NewMockRepository(ctrl *gomock.Controller) *MockRepository {
	mock := &MockRepository{ctrl: ctrl}
	mock.recorder = &MockRepositoryMockRecorder{mock}
	return mock
}

// EXPECT returns an object that allows the caller to indicate expected use.
func (m *MockRepository) EXPECT() *MockRepositoryMockRecorder {
	return m.recorder
}

// CloseDBConnection mocks base method.
func (m *MockRepository) CloseDBConnection() {
	m.ctrl.T.Helper()
	m.ctrl.Call(m, "CloseDBConnection")
}

// CloseDBConnection indicates an expected call of CloseDBConnection.
func (mr *MockRepositoryMockRecorder) CloseDBConnection() *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "CloseDBConnection", reflect.TypeOf((*MockRepository)(nil).CloseDBConnection))
}

// FindCity mocks base method.
func (m *MockRepository) FindCity(cityName string) (*model.City, error) {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "FindCity", cityName)
	ret0, _ := ret[0].(*model.City)
	ret1, _ := ret[1].(error)
	return ret0, ret1
}

// FindCity indicates an expected call of FindCity.
func (mr *MockRepositoryMockRecorder) FindCity(cityName any) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "FindCity", reflect.TypeOf((*MockRepository)(nil).FindCity), cityName)
}
