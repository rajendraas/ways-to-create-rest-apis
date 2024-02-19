import express from "express";
import { router } from "./router/router.js";
import swagger from "./swagger/swagger.js";
import dotenv from "dotenv";

dotenv.config();

const app = express();
const port = 3000;

app.use(router);
swagger(app);
app.use("/", (_, respose) => respose.send("Server is running..."));

app.listen(port, () => {
  console.log(`Listening on port ${port}...`);
});
