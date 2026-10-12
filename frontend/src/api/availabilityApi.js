import { api } from "./http";

export const getAvailability = (employeeId) =>
    api.get(`/employees/${employeeId}/availability`).then((response) => response.data);

export const saveAvailability = (employeeId, availability) =>
    api
        .post(`/employees/${employeeId}/availability`, availability)
        .then((response) => response.data);