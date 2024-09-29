from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, DeclarativeBase

from . import config

SQLALCHEMY_DATABASE_URL = f'mysql+pymysql://{config.MYSQLDB_USER}:{config.MYSQLDB_ROOT_PASSWORD}@{config.MYSQLDB_HOST}:3306/{config.MYSQLDB_DATABASE}'

db_engine = create_engine(SQLALCHEMY_DATABASE_URL)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=db_engine)


class Base(DeclarativeBase):
    pass
