import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    server: {
        host: '0.0.0.0', // Expose to Docker
        port: 5173,
        proxy: {
            '/api': {
                target: 'http://api-gateway:8080',
                changeOrigin: true,
                secure: false
            }
        }
    }
})
