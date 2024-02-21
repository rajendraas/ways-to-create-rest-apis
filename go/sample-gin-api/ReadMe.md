Commands that I've run -

- `go install github.com/onsi/ginkgo/v2/ginkgo`
- `go install go.uber.org/mock/mockgen@latest`
- `cd service && ginkgo bootstrap`
- `mockgen --source=repository/repository.go --destination=mocks/repository_mock.go --package=mock`
- `mockgen --source=client/openmeteo.go --destination=mocks/openmeteoClient_mock.go --package=mock`
- `mockgen --source=client/sunrisesunset.go --destination=mocks/sunrisesunsetClient_mock.go --package=mock`
- `mockgen --source=service/service.go --destination=mocks/service_mock.go --package=mock`
- `mockgen --source=controller/controller.go --destination=mocks/controller_mock.go --package=mock`