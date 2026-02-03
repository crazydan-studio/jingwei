import { defineConfig } from 'vite';

// https://vite.dev/config/
export default defineConfig({
  build: {
    outDir: 'dist',
    minify: true,
    target: 'node20',
    lib: {
      entry: 'src/cli.js',
      formats: ['es'],
      fileName: 'browser'
    },
    rollupOptions: {
      // 排除 Node 内置模块
      external: [
        /^node:/,
        'fs',
        'path',
        'os',
        'events',
        //
        'playwright-core'
      ]
    }
  },
  plugins: []
});
