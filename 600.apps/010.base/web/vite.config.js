import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { viteExternalsPlugin } from 'vite-plugin-externals';
import { compression } from 'vite-plugin-compression2';
import { ViteImageOptimizer } from 'vite-plugin-image-optimizer';
import { createHtmlPlugin } from 'vite-plugin-html';
import { viteStaticCopy } from 'vite-plugin-static-copy';

// https://vite.dev/config/
export default defineConfig({
  build: {
    copyPublicDir: true,
    rollupOptions: {
      input: {
        index: 'index.html',
        main: 'src/main.js'
      },
      output: {
        entryFileNames: '[name].js'
      }
    }
  },
  plugins: [
    vue(),
    viteExternalsPlugin({
      vue: 'Vue',
      'naive-ui': 'naive',
      '@app-utils': 'AppUtils'
    }),
    viteStaticCopy({
      targets: [
        {
          src: 'node_modules/vue/dist/vue.global.prod.js',
          dest: 'assets',
          rename: 'vue.min.js'
        },
        {
          src: 'node_modules/naive-ui/dist/index.prod.js',
          dest: 'assets',
          rename: 'naive-ui.min.js'
        },
        //
        {
          src: 'node_modules/@tailwindcss/browser/dist/index.global.js',
          dest: 'assets',
          rename: 'tailwindcss.min.js'
        },
        //
        {
          src: 'node_modules/@highlightjs/cdn-assets/highlight.min.js',
          dest: 'assets',
          rename: 'highlightjs.min.js'
        },
        {
          src: 'node_modules/@highlightjs/cdn-assets/styles/atom-one-dark.min.css',
          dest: 'assets/highlightjs',
          rename: 'atom-one-dark.min.css'
        },
        {
          src: 'node_modules/@highlightjs/cdn-assets/styles/atom-one-light.min.css',
          dest: 'assets/highlightjs',
          rename: 'atom-one-light.min.css'
        }
      ]
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
