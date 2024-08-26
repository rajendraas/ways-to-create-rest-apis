import os

from dotenv import load_dotenv

load_dotenv()

MYSQLDB_HOST = os.getenv("MYSQLDB_HOST")
MYSQLDB_USER = os.getenv("MYSQLDB_USER")
MYSQLDB_ROOT_PASSWORD = os.getenv("MYSQLDB_ROOT_PASSWORD")
MYSQLDB_DATABASE = os.getenv("MYSQLDB_DATABASE")
# DEBUG = os.getenv("DEBUG") == "True"
