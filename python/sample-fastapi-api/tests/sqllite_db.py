from sqlalchemy import create_engine, text
from sqlalchemy.orm import sessionmaker
from sqlalchemy.pool import StaticPool

from app.database import Base

SQLALCHEMY_DATABASE_URL = "sqlite://"

engine = create_engine(
    SQLALCHEMY_DATABASE_URL,
    connect_args={"check_same_thread": False},
    poolclass=StaticPool,
)
TestingSessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

insert_pune_city_statement = text(
    """INSERT INTO cities(city,lat,lng) 
            SELECT 'PUNE','18.516726','73.856255' 
            WHERE NOT EXISTS(SELECT 1 FROM cities WHERE city = 'PUNE')"""
)


def sqllite_db():
    Base.metadata.create_all(bind=engine)
    db = TestingSessionLocal()
    try:
        db.execute(insert_pune_city_statement)

        yield db
    finally:
        db.close()
