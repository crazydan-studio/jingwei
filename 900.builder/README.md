# 精卫平台的应用构建器

## Web 构建

用于统一应用 Web 前端的依赖模块及其版本，以便于集中安装所需的相关依赖到 `node_modules` 
目录，再通过配置项 `jingwei.app.build.node_modules` 使得在本地构建应用时可以共享该依赖库，
确保应用构建的稳定性。

在首次构建本平台或者 `package.json` 有更新时，需运行依赖安装命令 `npm install`
以安装全部依赖。
