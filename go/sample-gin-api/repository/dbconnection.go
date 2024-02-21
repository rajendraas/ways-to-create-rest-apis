package repository

import (
	"log"
	"os"

	mysqlDriver "github.com/go-sql-driver/mysql"
	"github.com/joho/godotenv"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type DBConnection interface {
	GetDBConnection() *gorm.DB
	CloseDBConnection()
}

type dbConnection struct {
	connection *gorm.DB
}

func NewMySqlDbConnection() DBConnection {
	err := godotenv.Load(".env")

	if err != nil {
		log.Printf("Error loading .env file. Will continue if environment variables are present.")
	}

	db, err := gorm.Open(mysql.New(mysql.Config{
		DSNConfig: &mysqlDriver.Config{
			User:   os.Getenv("MYSQLDB_USER"),
			Passwd: os.Getenv("MYSQLDB_ROOT_PASSWORD"),
			Net:    "tcp",
			Addr:   os.Getenv("MYSQLDB_HOST"),
			DBName: os.Getenv("MYSQLDB_DATABASE"),
		},
	}), &gorm.Config{})
	if err != nil {
		panic("Failed to connect to database")
	}

	log.Printf("Database connection established.")
	return &dbConnection{connection: db}
}

func (db *dbConnection) GetDBConnection() *gorm.DB {
	return db.connection
}

func (db *dbConnection) CloseDBConnection() {
	sqlDB, dbErr := db.connection.DB()
	if dbErr != nil {
		log.Fatalf(dbErr.Error())
	}
	err := sqlDB.Close()
	if err != nil {
		log.Fatalf(err.Error())
	}
	log.Printf("Database connection closed.")
}
