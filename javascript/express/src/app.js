import express from "express";
import { router } from "./router.js";

const app = express();
const port = 3000;

app.use(router);

app.use("/", (_, respose) => respose.send("Server is running..."));

app.listen(port, () => {
  console.log(`Listening on port ${port}...`);
});
