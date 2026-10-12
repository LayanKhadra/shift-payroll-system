import axios from "axios";

export const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL ?? "http://localhost:8080",
});

export function messageFrom(error) {
    if (error.response?.data?.error) return error.response.data.error;
    if (error.request) return "Cannot reach the server. Is the backend running?";
    return "Something went wrong. Please try again.";
}
