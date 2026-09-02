# WeChat Mini Program Support

[English](README.md) | [简体中文](README.zh-CN.md)

## 功能

### 项目与文件支持

- 根据 `project.config.json` 自动识别微信小程序项目，也可在项目设置中手动启用小程序支持。
- 根据 `miniprogramRoot` 定位小程序源码目录和 `app.json`。
- 支持微信小程序的 WXML、WXSS、WXS 和 JSON 文件。
- 支持 QQ 小程序的 QML、QSS 和 QS 文件，并可在项目设置中切换小程序类型。
- 支持 npm 组件及 `miniprogram_npm` 中的组件。
- 支持在页面或组件的 JavaScript、WXML/QML、WXSS/QSS 和 JSON 文件之间快速跳转。

### WXML

- 提供 WXML 语法高亮、格式化、代码折叠和注释抑制。
- 提供内置标签、属性、事件和自定义组件的代码补全及快速文档。
- 识别页面 JSON 和 `app.json` 中通过 `usingComponents` 注册的自定义组件，并支持补全和跳转。
- 支持 `componentGenerics` 泛型组件、默认组件路径及 WXML `generic:xxx` 语法。
- 支持自定义组件属性补全和跳转，并将 kebab-case 属性名（如 `ab-cd`）映射到 camelCase 属性（如 `abCd`）。
- 支持 `model:xxx` 双向绑定属性的补全和引用解析。
- 支持 `id`、`class`、`externalClasses` 和具名 `slot` 的引用、查找使用、跳转及重命名。
- 支持 [`template`](https://developers.weixin.qq.com/miniprogram/dev/reference/wxml/template.html) 的定义、引用、跨文件查找、跳转和查找使用。
- 支持 `import`、`include`、`wxs src`、`image src` 和 `cover-image src` 等文件路径的跳转。
- 支持 WXML 中的 JavaScript 插值表达式，包括多行表达式及 `&` 等特殊字符。
- 支持从 WXML 表达式和事件绑定跳转到相关 JavaScript 属性或方法，包括 `ComponentWithComputed()` 声明的成员。
- 支持从 WXML 跳转到组件引用的 Behavior 中声明的 `data`、`properties` 和 `methods`，并支持嵌套 Behavior。
- 支持在内联 `<wxs>` 中编写 JavaScript。
- 支持预览 WXML 中的颜色值。

### WXSS 与样式预处理器

- 提供 WXSS 语法高亮、格式化、代码折叠、注释、面包屑和属性补全。
- 支持 WXML 与 WXSS 之间的 class、id 和 `externalClasses` 引用跳转、查找使用及重命名。
- 支持 `@import` 文件路径和 `animation` / `animation-name` 关键帧引用的跳转。
- 支持 Less、Sass、SCSS 和 Stylus 文件中的小程序样式语法、格式化及检查兼容。

### JSON 配置

- 为 `app.json`、页面配置和组件配置提供中文 JSON Schema、校验及代码补全。
- 支持 `pages`、`entryPagePath`、`subpackages.pages` 和 `tabBar.list.pagePath` 的页面路径跳转。
- 支持 `usingComponents` 和 `componentGenerics` 中的组件路径跳转，包括相对路径、绝对路径及 npm 组件路径。
- 支持从 `usingComponents` 中的组件注册名跳转到 WXML 标签，并支持查找使用和重命名。

### 创建、重构与快速修复

- 可通过 IDE 的 **New** 菜单创建页面或组件，并自动生成对应的 JavaScript、WXML/QML、WXSS/QSS 和 JSON 文件。
- 创建页面时可选择 Page API 或 Component API，并自动将页面注册到 `app.json`。
- 可将选中的 WXML 标签提取为新组件，同时创建组件文件、替换原标签并在当前 JSON 文件中注册组件。
- 可从 WXML 事件绑定快速创建对应的 Page 事件处理器或 Component `methods` 方法。
- 可从 WXML 的 class 或 id 快速在组件样式文件或 `app.wxss` 中创建选择器。
- 重命名页面或组件文件时，可同步重命名同组文件并更新相关引用。
- 移动页面或组件文件时，可更新对应的组件路径引用。
- 检查无效的 WXML `import` / `include`、WXS `src` 和 WXSS `@import` 路径，并提供相应快速修复。

## 安装

此 Fork 不支持通过 IDE 插件市场安装。

1. 从 [GitHub Releases](https://github.com/iniceice88/wechat-miniprogram-ijplugin/releases) 下载插件 ZIP 文件，请勿解压。
2. 在 JetBrains IDE 中打开 **Settings/Preferences | Plugins**。
3. 点击齿轮图标，选择 **Install Plugin from Disk...**。
4. 选择下载的 ZIP 文件，并在提示时重启 IDE。

## 使用

使用 IDE 打开微信小程序或 QQ 小程序项目，并确保项目中存在 `project.config.json` 文件，插件会自动启用相关功能。

## 许可证

本项目使用[木兰宽松许可证，第 1 版](LICENSE)。
