import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // cualquier petición que empiece con /api se redirige al backend real
      '/api': {
        target: 'http://localhost:8080', // tu backend real
        changeOrigin: true,
        secure: false,
      },
    },
  },
})