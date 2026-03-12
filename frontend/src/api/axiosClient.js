import axios from "axios";

const axiosClient = axios.create({
  baseURL: "/api"
});

export const api = {

  login: async ({ username, password }) => {

    const response = await axiosClient.post(
      "/usuarios/login",
      null,
      {
        params: {
          usuario: username,
          password: password
        }
      }
    );

    return response.data;
  }

};