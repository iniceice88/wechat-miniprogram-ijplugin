# wechat-miniprogram-plugin

> [!IMPORTANT]
> 推荐使用 [官方插件](https://plugins.jetbrains.com/plugin/24687-wechat-mini-program)

### Fork自[wechat-miniprogram-plugin](https://gitee.com/zxy_c/wechat-miniprogram-plugin)，在原有基础上增加了一些功能

### BUG修复

- 根据`project.config.json`中的`miniprogramRoot`配置，识别`app.json`文件

#### wxml文件代码块`{{}}`中可正常写js代码。原来是不能换行,不能有'&'等特殊字符，也不能跳转/代码提示。

### 功能增加

- `app.json`文件更多跳转路径支持。包括`entryPagePath`, `subpackages`,`tabBar`
- `index.json`文件支持componentGenerics下组件的路径跳转
- 对于componentGenerics组件,wxml里不再报错，且支持跳转
- 从wxml跳转到js文件对应 属性/方法 时,支持`ComponentWithComputed()`
- wxml中`generic:xxx`写法不再报错
- wxml中自定义组件的属性写法支持`ab-cd`的写法，实际对应的是`abCd`属性
- [template](https://developers.weixin.qq.com/miniprogram/dev/reference/wxml/template.html) wxml中使用`app.json`中的`usingComponents`时不报错，且支持跳转
- JSON Schema改为中文

### TODO

- [ ] 处理wxml到Behavior的跳转
- [ ] 调查：this.data.xxx无法跳转

### 原版功能

在[Wiki](https://gitee.com/zxy_c/wechat-miniprogram-plugin/wikis)中浏览更多功能

### 安装

**不支持通过IDE的插件市场安装**

下载[发行版](https://github.com/iniceice88/wechat-miniprogram-ijplugin/releases)附件中的zip文件，在IDE中选择从磁盘安装

### 使用

通过IDE打开微信小程序项目即可使用全部功能