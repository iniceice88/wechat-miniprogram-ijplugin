https://github.com/JetBrains/intellij-community/tree/idea/242.20224.387/platform/core-api

### 注册一个跳转动作

1. 创建自定义的 PsiReferenceContributor
2. 注册 PsiReferenceContributor 到插件中
3. 实现 PsiReferenceProvider
4. 创建自定义的 PsiReference

### TypedHandlerDelegate

`TypedHandlerDelegate` 是 IntelliJ IDEA 平台中的一个类，用于处理用户在编辑器中键入字符时的事件。它允许开发者在特定字符被键入之前或之后执行自定义逻辑。
通过扩展 `TypedHandlerDelegate`，可以拦截和处理键入事件，以实现诸如自动补全、格式化、插入特定字符等功能。

以下是 `TypedHandlerDelegate` 的主要方法：

- `beforeCharTyped`: 在字符被键入之前调用，可以用于决定是否继续处理该字符。
- `charTyped`: 在字符被键入之后调用，可以用于执行字符插入后的操作。

### wxml代码提示

WXMLAttributeNameCompletionProvider

### wxml注入js

WxmlJSInjector

### wxml自定义标签，Ctrl+点击跳转到对应json的usingComponents中的路径

WXMLElementDescriptorProvider

### Namespace wxml中使用generic:xx的时候报错：'generic' is not bound

修改：WxmlXmlExtension

## TODO

- [x] 从wxml点击方法/属性/data的时候，弹出来的是全部js文件的，应该只弹出当前页面的js文件
- [x] 属性有时候是aa-bb的写法，其实对应的是aaBb,也应该可以正常跳转
- [ ] 模板语法无法识别{{obj && obj.xxx}}，原因：在wxml中，&是转义字符，需要转义
- [x] 处理componentGenerics, generic:xxx
- [x] 对于独立的template.wxml文件，如果用到了app.json中的usingComponents，无法跳转
- [ ] 处理Behavior跳转
- [ ] 调查：this.data.xxx的跳转
- [ ] {{}}中的内容无法换行

```kt
private val LOG = Logger.getInstance("WxmlJsInspectionFilter")
```

### 疑问

Q: 点击index.json中的url时为什么可以跳转到miniprogram文件夹下的文件，是在哪里设置的？
A: ComponentFileReferenceHelper

Q: Ctrl点击自定义组件的属性时，为什么会跳转到js文件的对应properties位置？
A: WxmlCustomComponentDescriptor.getAttributeDescriptor