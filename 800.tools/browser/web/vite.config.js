import { fileURLToPath, URL } from 'node:url';

import { defineConfig } from 'vite';

// https://vite.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    outDir: 'dist',
    ssr: true,
    minify: true,
    target: 'node20',
    lib: {
      entry: 'src/main.js',
      formats: ['es']
    },
    rollupOptions: {
      external: [
        // 排除 Node 内置模块
        /^node:/,
        'fs',
        'path',
        'os',
        'events',
        //
        'playwright-core',
        'fastify'
      ]
    }
  },
  plugins: []
});
