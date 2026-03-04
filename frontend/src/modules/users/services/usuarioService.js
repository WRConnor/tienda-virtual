import { apiClient } from "@/core/api/apiClient";

export const getUsuarios = async () => {
  return apiClient("/usuarios");
};