import { Router } from "express";
import apis from "../controller/city.controller.js";

export const router = Router().use(apis);
