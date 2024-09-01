package com.zxy.ijplugin.wechat_miniprogram.utils

object StringUtils {

  /**
   * 判断字符串中是否包含指定的字符串，且不在引号内
   * input=aaa: "bbb" mark=: return=true
   * input=aaa ":bbb" mark=: return=false
   */
  fun containsNoneStringMark(input: String, mark: String): Boolean {
    return findNoneStringMarkIndex(input, mark) != -1
  }

  private fun findNoneStringMarkIndex(input: String, mark: String): Int {
    var idx = input.indexOf(mark)
    while (idx > 0) {
      if (!isMarkInsideQuotes(input, idx)) {
        return idx
      }
      idx = input.indexOf(mark, idx + 1)
    }
    return -1
  }

  /**
   * 简单判断mark是否在括号内,忽略字符串(''或"")中的字符
   * 例:
   * input=aaa(bbb&&cc) mark=&& return=true
   * input=aaa(bbb)&&cc mark=&& return=false
   */
  fun isMarkInsideParenthesis(input: String, mark: String): Boolean {
    val idx = findNoneStringMarkIndex(input, mark)
    if (idx == -1) {
      return false
    }
    var inDelimiters = false
    var insideParenthesis = false
    for (i in 0 until idx) {
      when (input[i]) {
        '\'' -> inDelimiters = !inDelimiters
        '\"' -> inDelimiters = !inDelimiters
        '(' -> if (!inDelimiters) insideParenthesis = true
      }
    }
    return insideParenthesis
  }

  /**
   * 判断字符是否在指定的分隔符内
   */
  fun isCharInsideDelimiters(input: String, charIdx: Int, openingChar: Char, closingChar: Char): Boolean {
    var inDelimiters = false

    for (i in 0 until charIdx) {
      when (input[i]) {
        openingChar -> inDelimiters = !inDelimiters
        closingChar -> if (inDelimiters) inDelimiters = !inDelimiters
      }
    }

    // 检查是否在指定的分隔符内
    return inDelimiters
  }


  /**
   * 判断字符是否在引号内
   */
  private fun isMarkInsideQuotes(input: String, markIdx: Int): Boolean {
    return isCharInsideDelimiters(input, markIdx, '"', '"') ||
      isCharInsideDelimiters(input, markIdx, '\'', '\'')
  }
}
