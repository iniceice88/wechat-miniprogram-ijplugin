# WeChat Mini Program Support

[English](README.md) | [简体中文](README.zh-CN.md)

## Features

### Project and file support

- Automatically detects WeChat Mini Program projects from `project.config.json`, with an option to enable Mini Program support manually in the project settings.
- Locates the Mini Program source directory and `app.json` according to `miniprogramRoot`.
- Supports WeChat Mini Program WXML, WXSS, WXS, and JSON files.
- Supports QQ Mini Program QML, QSS, and QS files, with a project setting for switching the Mini Program type.
- Supports npm components and components under `miniprogram_npm`.
- Provides quick navigation between the JavaScript, WXML/QML, WXSS/QSS, and JSON files belonging to a page or component.

### WXML

- Provides WXML syntax highlighting, formatting, code folding, and inspection suppression.
- Provides code completion and quick documentation for built-in tags, attributes, events, and custom components.
- Recognizes custom components registered through `usingComponents` in page JSON files and `app.json`, with completion and navigation support.
- Supports `componentGenerics`, default generic component paths, and the WXML `generic:xxx` syntax.
- Provides completion and navigation for custom component properties, mapping kebab-case attributes such as `ab-cd` to camelCase properties such as `abCd`.
- Supports completion and reference resolution for `model:xxx` two-way bindings.
- Supports references, Find Usages, navigation, and rename refactoring for `id`, `class`, `externalClasses`, and named `slot` values.
- Supports definitions, references, cross-file lookup, navigation, and Find Usages for WXML [`template`](https://developers.weixin.qq.com/miniprogram/dev/reference/wxml/template.html) elements.
- Provides file navigation for `import`, `include`, `wxs src`, `image src`, and `cover-image src` paths.
- Supports JavaScript interpolation expressions in WXML, including multiline expressions and special characters such as `&`.
- Navigates from WXML expressions and event bindings to related JavaScript properties and methods, including members declared with `ComponentWithComputed()`.
- Navigates from WXML to `data`, `properties`, and `methods` declared by a component's Behaviors, including nested Behaviors.
- Supports JavaScript inside inline `<wxs>` elements.
- Provides color previews for color values in WXML.

### WXSS and style preprocessors

- Provides WXSS syntax highlighting, formatting, code folding, commenting, breadcrumbs, and property completion.
- Supports navigation, Find Usages, and rename refactoring for class, id, and `externalClasses` references between WXML and WXSS.
- Provides navigation for `@import` paths and keyframe references in `animation` and `animation-name` declarations.
- Supports Mini Program style syntax, formatting, and inspection compatibility in Less, Sass, SCSS, and Stylus files.

### JSON configuration

- Provides Chinese JSON Schemas, validation, and code completion for `app.json`, page configuration, and component configuration files.
- Provides page path navigation for `pages`, `entryPagePath`, `subpackages.pages`, and `tabBar.list.pagePath`.
- Provides component path navigation for `usingComponents` and `componentGenerics`, including relative, absolute, and npm component paths.
- Navigates from component registration names in `usingComponents` to WXML tags, with Find Usages and rename support.

### Creation, refactoring, and quick fixes

- Creates pages or components from the IDE's **New** menu, generating the corresponding JavaScript, WXML/QML, WXSS/QSS, and JSON files.
- Allows new pages to use either the Page API or Component API and automatically registers them in `app.json`.
- Extracts selected WXML tags into a new component, creates its files, replaces the selected markup, and registers the component in the current JSON file.
- Creates Page event handlers or Component `methods` directly from WXML event bindings.
- Creates selectors in the component stylesheet or `app.wxss` directly from WXML class and id attributes.
- Renames related page or component files together and updates their references.
- Updates component path references when page or component files are moved.
- Detects invalid WXML `import` / `include`, WXS `src`, and WXSS `@import` paths and provides the corresponding quick fixes.

## Installation

This fork is not available through the IDE plugin marketplace.

1. Download the plugin ZIP from the [GitHub Releases](https://github.com/iniceice88/wechat-miniprogram-ijplugin/releases) page. Do not extract it.
2. In your JetBrains IDE, open **Settings/Preferences | Plugins**.
3. Click the gear icon and select **Install Plugin from Disk...**.
4. Select the downloaded ZIP file and restart the IDE when prompted.

## Usage

Open a WeChat Mini Program or QQ Mini Program project in the IDE. Make sure the project contains a `project.config.json` file; the plugin will then enable its project features automatically.

## License

This project is licensed under the [Mulan Permissive Software License, Version 1](LICENSE).
