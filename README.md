# wechat-miniprogram-plugin

### Fork自[wechat-miniprogram-plugin](https://gitee.com/zxy_c/wechat-miniprogram-plugin)，在原有基础上增加了一些功能

### BUG修复

- 根据`project.config.json`中的`miniprogramRoot`配置，识别`app.json`文件

#### 修复了wxml文件代码块`{{}}`中下列几种情况的js错误提示

- `{{ ...obj }}`
- `{{ { age: 18 } }}`
- `{{ age: 18, name: 'user' }}`
- `{{ obj && obj.xxx }}` 有限支持。比较复杂的表达式如
  `{{ (line.name1 || '') + ((line.name1 && line.name2 ) ? ' ' : '' ) }}`当作普通文本处理,不再报错但同时无法提示/跳转
- `{{}}` 中支持换行

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

下载[发行版](https://github.com/iniceice88/wechat-miniprogram-ijplugin/releases)附件中的jar文件，在IDE中选择从磁盘安装

### 使用

通过IDE打开微信小程序项目即可使用全部功能