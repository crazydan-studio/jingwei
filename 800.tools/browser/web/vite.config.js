import { defineConfig } from 'vite';

// https://vite.dev/config/
export default defineConfig({
  build: {
    outDir: 'dist',
    ssr: true,
    minify: true,
    target: 'node20',
    lib: {
      entry: 'src/server.js',
      formats: ['es'],
      fileName: 'browser'
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
