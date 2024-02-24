# REST API implementation with Gin framework

This project contains REST API implementation example with Gin framework. You'll need basic knowledge of GoLang before you can start exploring this project.

## Steps to run server locally

Before you start the server, please start the MySQL server.

To run the server locally -
`go run main.go`

You don't need to run any specific server to run this project, like we need Tomcat server in some Java based REST API implementation.

Access the API through http://localhost:8080/city/pune

## Unit tests

This project also includes the unit tests for the implementation.

To run all the unit test - `go test ./...`

While creating this project, I've run some commands for test suite creation. Here are those -

- To install ginkgo - `go install github.com/onsi/ginkgo/v2/ginkgo`
- To create testsuties for each package - `cd <package folder> && ginkgo bootstrap`
- To generate mocks 
    - `go install go.uber.org/mock/mockgen@latest`
    - `mockgen --source=repository/repository.go --destination=mocks/repository_mock.go --package=mock`
    - `mockgen --source=client/openmeteo.go --destination=mocks/openmeteoClient_mock.go --package=mock`
    - `mockgen --source=client/sunrisesunset.go --destination=mocks/sunrisesunsetClient_mock.go --package=mock`
    - `mockgen --source=service/service.go --destination=mocks/service_mock.go --package=mock`
    - `mockgen --source=controller/controller.go --destination=mocks/controller_mock.go --package=mock`