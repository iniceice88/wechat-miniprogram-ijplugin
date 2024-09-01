package com.zxy.ijplugin.wechat_miniprogram.utils

import com.intellij.json.psi.*

object JsonElementUtils {
  /**
   * 判断路径是否匹配，从子元素到父元素
   * 规则：
   * [] 表示数组
   * . 表示对象或者属性，不关心属性名
   * $ 表示根元素(JsonFile)
   *
   * 例如：[] pages . [] subpackages . $ 可以匹配subpackages下的page数组内容
   */
  fun isParentMatch(ele: JsonElement, rules: List<String>): Boolean {
    var parent = ele.parent
    if (parent == null) return false

    for (rule in rules) {
      if (parent == null || parent !is JsonElement) {
        return false
      }
      if (!isMatch(parent, rule)) {
        return false
      }
      if (rule == "$") return true
      val p = parent.parent
      if (p == null || p !is JsonElement) {
        return false
      }
      parent = p
    }
    return true
  }

  /**
   * 取得当前元素到根元素(JsonFile)的路径
   * 使用该路径调用isParentMatch应该返回true
   */
  fun getPathToRoot(ele: JsonElement): List<String> {
    val list = mutableListOf<String>()
    var parent = ele.parent
    while (parent != null && parent is JsonElement) {
      when (parent) {
        is JsonProperty -> list.add(parent.name)
        is JsonObject -> list.add(".")
        is JsonArray -> list.add("[]")
      }
      parent = parent.parent
      if (parent is JsonFile) {
        list.add("$")
        break
      }
    }
    return list
  }

  // 递归查找目标字符串所在的 JsonObject
  fun findTargetJsonObject(element: JsonValue, targetValue: String): JsonValue? {
    when (element) {
      is JsonStringLiteral -> {
        if (element.text == targetValue) {
          return element
        }
      }

      is JsonObject -> {
        // 遍历 JsonObject 的属性
        element.propertyList.forEach { property ->
          val value = property.value ?: return@forEach
          val found = findTargetJsonObject(value, targetValue)
          if (found != null) {
            return found
          }
        }
      }

      is JsonArray -> {
        // 如果当前元素是数组，递归检查数组中的每个元素
        element.valueList.forEach { arrayElement ->
          val found = findTargetJsonObject(arrayElement, targetValue)
          if (found != null) {
            return found
          }
        }
      }
    }
    return null
  }

  private fun isMatch(ele: JsonElement, rule: String): Boolean {
    if (rule == "[]") {
      return ele is JsonArray
    }
    if (rule == "$") {
      return ele is JsonFile
    }
    if (rule == ".") {
      return ele is JsonObject || ele is JsonProperty
    }
    return ele.name == rule
  }
}
