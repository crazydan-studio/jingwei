import { resolve } from 'path';

import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { viteExternalsPlugin } from 'vite-plugin-externals';
import { compression } from 'vite-plugin-compression2';

// https://vite.dev/config/
export default defineConfig({
  build: {
    outDir: resolve(__dirname, '../../dist'),
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
      entry: 'src/index.js',
      name: 'AppUtils',
      formats: ['iife'], // 按指定模块名做全局导出
      fileName: (format, entryName) => 'utils.js'
    }
  },
  plugins: [
    vue(),
    viteExternalsPlugin({
      vue: 'Vue',
      'naive-ui': 'naive'
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
