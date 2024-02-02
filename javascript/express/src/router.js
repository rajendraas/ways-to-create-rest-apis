import { Router } from "express";
import apis from "./city.controller.js";

export const router = Router().use(apis);
