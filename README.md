# WeChat Mini Program Support

[English](README.md) | [简体中文](README.zh-CN.md)

IntelliJ Platform plugin support for WeChat Mini Program projects, with language support, navigation, inspections, and project file generation for WXML, WXSS, WXS, and related configuration files.

> [!IMPORTANT]
> For most users, the [official WeChat Mini Program plugin](https://plugins.jetbrains.com/plugin/24687-wechat-mini-program) is recommended.

This project is a fork of [wechat-miniprogram-plugin](https://gitee.com/zxy_c/wechat-miniprogram-plugin) with additional features and fixes.

## Improvements in this fork

### Bug fixes

- Locates `app.json` according to the `miniprogramRoot` setting in `project.config.json`.
- Improves JavaScript support inside WXML `{{ }}` expressions. Expressions can contain line breaks and special characters such as `&`, while code completion and navigation continue to work.

### Additional features

- Adds path navigation for more fields in `app.json`, including `entryPagePath`, `subpackages`, and `tabBar`.
- Supports component path navigation under `componentGenerics` in page and component JSON files.
- Recognizes generic components in WXML and provides navigation without false error reports.
- Supports navigation from WXML bindings to properties and methods declared with `ComponentWithComputed()`.
- Recognizes the `generic:xxx` syntax in WXML without reporting errors.
- Maps kebab-case custom component attributes such as `ab-cd` to camelCase properties such as `abCd`.
- Recognizes components registered through `usingComponents` in `app.json` when used in WXML [`template`](https://developers.weixin.qq.com/miniprogram/dev/reference/wxml/template.html) files, including navigation support.
- Provides Chinese JSON Schemas.

## Core features

- WXML, WXSS, and WXS language support.
- WeChat Mini Program page and component generation.
- Navigation between related files, components, properties, methods, styles, and templates.
- Custom component and configuration file support.
- Code inspections and quick fixes.
- QQ Mini Program project support.
- npm component support.

See the [upstream Wiki](https://gitee.com/zxy_c/wechat-miniprogram-plugin/wikis) for more detailed documentation about the original feature set.

## Installation

This fork is not available through the IDE plugin marketplace.

1. Download the plugin ZIP from the [GitHub Releases](https://github.com/iniceice88/wechat-miniprogram-ijplugin/releases) page. Do not extract it.
2. In your JetBrains IDE, open **Settings/Preferences | Plugins**.
3. Click the gear icon and select **Install Plugin from Disk...**.
4. Select the downloaded ZIP file and restart the IDE when prompted.

## Usage

Open a WeChat Mini Program or QQ Mini Program project in the IDE. Make sure the project contains a `project.config.json` file; the plugin will then enable its project features automatically.

## Roadmap

- [ ] Support navigation from WXML to behaviors.
- [ ] Investigate navigation for `this.data.xxx` references.

## License

This project is licensed under the [Mulan Permissive Software License, Version 1](LICENSE).
