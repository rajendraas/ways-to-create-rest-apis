import connectionPool from "../config/mysql.db.js";

export const getCityCoordinates = async (cityName) => {
  try {
    const [result, _] = await connectionPool.query(
      `select * from cities where city= ?`,
      [cityName]
    );
    return result[0];
  } catch (err) {
    console.error(err);
    return;
  }
};
