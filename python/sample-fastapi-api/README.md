# REST API implementation with FastAPI

This project contains REST API implementation example with FastAPI. You'll need basic knowledge of Python before you can start exploring this project.

## Steps to run server locally

Before you start the server, please start the MySQL server.

To run the server locally -
`fastapi dev main.py`

You don't need to run any specific server to run this project, like we need Tomcat server in some Java based REST API implementation.

Access the API through http://localhost:8000/city/pune

## Unit tests

This project currently does not include any the unit tests for the implementation.


## Commands

Following are some of the commands I've used setup the project

1. `virtualenv .fastapienv`
2. `source .fastapienv/bin/activate`
3. `pip install fastapi[standard]`
4. `pip install sqlalchemy`
5. `pip install pymysql`
