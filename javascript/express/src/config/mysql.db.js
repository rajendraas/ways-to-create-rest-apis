import mysql from "mysql2";

const connectionPool = mysql.createPool({
  host: "localhost",
  user: "root",
  password: "root",
  database: "sample-data",
  connectionLimit: 2,
});

export default connectionPool.promise();
