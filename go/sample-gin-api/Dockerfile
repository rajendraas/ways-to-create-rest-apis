FROM golang:1.22.0-alpine3.19 AS build

WORKDIR /app
COPY . .

RUN go mod download
RUN go build -o sample-gin-api

FROM alpine:3.19 As execute

WORKDIR /app
COPY --from=build /app/sample-gin-api .

CMD ["./sample-gin-api"]
