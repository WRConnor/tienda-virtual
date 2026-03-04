const API_URL = import.meta.env.VITE_API_URL;

export const apiClient = async (endpoint, options = {}) => {
  const response = await fetch(`${API_URL}${endpoint}`, {
    ...options,
    credentials: "include", // importante si usan sesión
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
  });

  if (response.status === 401) {
    window.location.href = "/login";
  }

  return response;
};

export default apiClient;