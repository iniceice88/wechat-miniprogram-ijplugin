# WeChat Mini Program Support

[English](README.md) | [简体中文](README.zh-CN.md)

> [!IMPORTANT]
> 推荐使用[官方插件](https://plugins.jetbrains.com/plugin/24687-wechat-mini-program)。

本项目 Fork 自 [wechat-miniprogram-plugin](https://gitee.com/zxy_c/wechat-miniprogram-plugin)，并在原有基础上增加了一些功能和修复。

## Bug 修复

- 根据 `project.config.json` 中的 `miniprogramRoot` 配置识别 `app.json` 文件。
- 改进 WXML `{{ }}` 表达式中的 JavaScript 支持：允许换行和 `&` 等特殊字符，同时支持代码补全和跳转。

## 新增功能

- 为 `app.json` 中的更多字段提供路径跳转，包括 `entryPagePath`、`subpackages` 和 `tabBar`。
- 支持页面及组件 JSON 文件中 `componentGenerics` 下的组件路径跳转。
- 在 WXML 中正确识别泛型组件，不再误报错误，并支持跳转。
- 从 WXML 跳转到 JavaScript 中对应的属性或方法时，支持 `ComponentWithComputed()`。
- WXML 中的 `generic:xxx` 写法不再报错。
- 自定义组件属性支持 `ab-cd` 写法，并映射到实际的 `abCd` 属性。
- 在 WXML [`template`](https://developers.weixin.qq.com/miniprogram/dev/reference/wxml/template.html) 文件中使用 `app.json` 里通过 `usingComponents` 注册的组件时，不再报错并支持跳转。
- 提供中文 JSON Schema。

## 原版功能

- WXML、WXSS 和 WXS 文件支持。
- 创建微信小程序页面和组件。
- 相关文件、组件、属性、方法、样式及模板之间的导航。
- 微信小程序自定义组件和配置文件支持。
- 代码检查和快速修复。
- QQ 小程序项目支持。
- npm 组件支持。

更多原版功能说明请参阅[上游 Wiki](https://gitee.com/zxy_c/wechat-miniprogram-plugin/wikis)。

## 安装

此 Fork 不支持通过 IDE 插件市场安装。

1. 从 [GitHub Releases](https://github.com/iniceice88/wechat-miniprogram-ijplugin/releases) 下载插件 ZIP 文件，请勿解压。
2. 在 JetBrains IDE 中打开 **Settings/Preferences | Plugins**。
3. 点击齿轮图标，选择 **Install Plugin from Disk...**。
4. 选择下载的 ZIP 文件，并在提示时重启 IDE。

## 使用

使用 IDE 打开微信小程序或 QQ 小程序项目，并确保项目中存在 `project.config.json` 文件，插件会自动启用相关功能。

## TODO

- [ ] 处理从 WXML 到 Behavior 的跳转。
- [ ] 调查 `this.data.xxx` 无法跳转的问题。

## 许可证

本项目使用[木兰宽松许可证，第 1 版](LICENSE)。
