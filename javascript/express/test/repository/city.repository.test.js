import connectionPool from "../../src/config/mysql.db.js";
import { getCityCoordinates } from "../../src/repository/city.repository.js";

describe("City repository", () => {
  it("should return city coordinates when found", async () => {
    jest
      .spyOn(connectionPool, "query")
      .mockReturnValue(
        Promise.resolve([
          [{ city: "PUNE", lat: "18.516726", lng: "73.856255" }],
        ])
      );

    const cityCoordinates = await getCityCoordinates("pune");

    expect(connectionPool.query).toHaveBeenCalledWith(
      "select * from cities where city= ?",
      ["pune"]
    );
    expect(cityCoordinates).toStrictEqual({
      city: "PUNE",
      lat: "18.516726",
      lng: "73.856255",
    });
  });

  it("should return undefined when coordinates are not found", async () => {
    jest.spyOn(connectionPool, "query").mockReturnValue(Promise.resolve([]));

    const cityCoordinates = await getCityCoordinates("pune");

    expect(cityCoordinates).toBeUndefined();
  });

  it("should return undefined when there was an error while querying database", async () => {
    jest
      .spyOn(connectionPool, "query")
      .mockReturnValue(Promise.reject(new Error()));

    const cityCoordinates = await getCityCoordinates("pune");

    expect(cityCoordinates).toBeUndefined();
  });
});
