import { Router } from "express";
import { getCityInfo } from "../service/city.service.js";

const apis = Router();

/**
 * @swagger
 * /city/{cityName}:
 *   get:
 *     summary: Get city weather & sunrise, sunset
 *     parameters:
 *       - in: path
 *         name: cityName
 *         schema:
 *           type: string
 *         required: true
 *         description: City name
 *     responses:
 *      '200':
 *         description: OK
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 */
apis.get("/city/:cityName", async (request, respose) => {
  const cityInfo = await getCityInfo(request.params.cityName);
  respose.send(cityInfo);
});

export default apis;
