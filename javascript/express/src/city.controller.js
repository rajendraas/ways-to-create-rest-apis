import { Router } from "express";
import { getCityInfo } from "./city.service.js";

const apis = Router();

apis.get("/city/:cityName", async (request, respose) => {
  const cityInfo = await getCityInfo(request.params.cityName);
  respose.send(cityInfo);
});

export default apis;
