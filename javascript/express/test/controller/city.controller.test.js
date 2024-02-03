import request from "supertest";
import app from "../../src/controller/city.controller.js";
import { cityApiResponse as mockResponse } from "../responseData/cityApiResponse.js";

jest.mock("../../src/service/city.service.js", () => ({
  getCityInfo: jest.fn().mockReturnValue(() => Promise.resolve(mockResponse)),
}));

describe("City controller test", () => {
  it("should return success response", async () => {
    request(app)
      .get("/city/pune")
      .expect(200)
      .expect((response) => expect(response).toStrictEqual(mockResponse));
  });
});
