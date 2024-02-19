import mysql from "mysql2";
import dotenv from "dotenv";

dotenv.config();

const connectionPool = mysql.createPool({
  host: process.env.MYSQLDB_HOST,
  user: process.env.MYSQLDB_USER,
  password: process.env.MYSQLDB_ROOT_PASSWORD,
  database: process.env.MYSQLDB_DATABASE,
  connectionLimit: 2,
});

export default connectionPool.promise();
