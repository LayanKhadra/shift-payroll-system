import { api } from "./http";

export const getEmployees = () =>
    api.get("/employees").then((response) => response.data);