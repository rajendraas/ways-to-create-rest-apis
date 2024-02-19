import swaggerJsdoc from "swagger-jsdoc";
import { serve, setup } from "swagger-ui-express";
import path from "path";

const options = {
  swaggerDefinition: {
    swagger: "2.0",
    info: {
      title: "My API",
      version: "1.0.0",
      description: "My REST API",
    },
    servers: [{ url: "/" }],
  },
  apis: ["**/router.js", "**/*.controller.js"],
};

const specs = swaggerJsdoc(options);

export default (app) => {
  app.use("/swagger-ui", serve, setup(specs));
};
