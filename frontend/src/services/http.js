const API_URL = "http://localhost:8080";

const http = {
  post: async (endpoint, body) => {
    const response = await fetch(`${API_URL}${endpoint}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    });

    if (!response.ok) {
      throw new Error("Error en la petición");
    }

    return await response.json();
  }
};

export default http;