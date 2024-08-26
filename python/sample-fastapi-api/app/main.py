from dotenv import load_dotenv
from fastapi import FastAPI

from . import router

load_dotenv()

app = FastAPI()
app.include_router(router.city_router)
