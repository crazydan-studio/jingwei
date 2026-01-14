import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { viteExternalsPlugin } from 'vite-plugin-externals';
import { compression } from 'vite-plugin-compression2';
import { ViteImageOptimizer } from 'vite-plugin-image-optimizer';
import { createHtmlPlugin } from 'vite-plugin-html';

// https://vite.dev/config/
export default defineConfig({
  build: {
    copyPublicDir: true,
    rollupOptions: {
      output: {
        entryFileNames: 'index.js',
        assetFileNames: 'assets/[name].[ext]'
      }
    }
  },
  plugins: [
    vue(),
    viteExternalsPlugin({
      vue: 'Vue',
      'naive-ui': 'naive'
    }),
    // https://github.com/vbenjs/vite-plugin-html
    createHtmlPlugin({
      minify: true,
      inject: {
        data: {}
      }
    }),
    // https://www.npmjs.com/package/vite-plugin-image-optimizer
    ViteImageOptimizer({
      // 处理 public 目录下的图片
      //includePublic: true,
      svg: {
        multipass: true,
        plugins: [
          {
            name: 'preset-default',
            params: {
              overrides: {
                cleanupNumericValues: false
              },
              cleanupIDs: {
                minify: false,
                remove: false
              },
              convertPathData: false
            }
          },
          'sortAttrs',
          {
            name: 'addAttributesToSVGElement',
            params: {
              attributes: [{ xmlns: 'http://www.w3.org/2000/svg' }]
            }
          }
        ]
      }
    }),
    // https://github.com/nonzzz/vite-plugin-compression
    compression({
      algorithms: ['brotliCompress'],
      include: /\.(html|xml|css|json|js|mjs|svg|yaml|yml|toml)$/,
      // 涉及运行时对 index.html 的配置注入，故而不能对其做压缩
      exclude: /^(index\.html)$/,
      // 大于 1kb 则执行压缩
      threshold: 1024,
      // 是否删除原始非压缩文件
      deleteOriginalAssets: true
    })
  ]
});
