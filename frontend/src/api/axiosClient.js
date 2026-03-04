import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080", // luego aquí va la IP del gateway
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosClient;