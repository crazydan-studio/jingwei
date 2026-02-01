import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { viteExternalsPlugin } from 'vite-plugin-externals';
import { compression } from 'vite-plugin-compression2';

// https://vite.dev/config/
export default defineConfig({
  worker: {
    // Fix 'Invalid value "iife" for option "worker.format" - UMD and IIFE output formats are not supported for code-splitting builds'
    format: 'es'
  },
  build: {
    outDir: 'dist',
    // Note: 只有 terser 才支持压缩 es 模块代码
    minify: 'terser',
    terserOptions: {
      // 生产环境移除 console 和 debugger
      compress: {
        drop_console: true,
        drop_debugger: true
      }
    },
    lib: {
      entry: 'src/main.jsx',
      name: 'App',
      formats: ['es'],
      fileName: 'graphiql',
      cssFileName: 'graphiql'
    },
    rollupOptions: {
      output: {
        chunkFileNames: 'graphiql/[name]-[hash].js'
      }
    }
  },
  plugins: [
    react(),
    viteExternalsPlugin({
      '@app-utils': 'AppUtils'
    }),
    // https://github.com/nonzzz/vite-plugin-compression
    compression({
      algorithms: ['brotliCompress'],
      include: /\.(html|xml|css|json|js|mjs|svg|yaml|yml|toml)$/,
      // 大于 1kb 则执行压缩
      threshold: 1024,
      // 是否删除原始非压缩文件
      deleteOriginalAssets: true
    })
  ]
});
