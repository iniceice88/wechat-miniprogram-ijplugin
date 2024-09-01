/*
 *    Copyright (c) [2019] [zxy]
 *    [wechat-miniprogram-plugin] is licensed under the Mulan PSL v1.
 *    You can use this software according to the terms and conditions of the Mulan PSL v1.
 *    You may obtain a copy of Mulan PSL v1 at:
 *       http://license.coscl.org.cn/MulanPSL
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 *    PURPOSE.
 *    See the Mulan PSL v1 for more details.
 *
 *
 *                      Mulan Permissive Software License，Version 1
 *
 *    Mulan Permissive Software License，Version 1 (Mulan PSL v1)
 *    August 2019 http://license.coscl.org.cn/MulanPSL
 *
 *    Your reproduction, use, modification and distribution of the Software shall be subject to Mulan PSL v1 (this License) with following terms and conditions:
 *
 *    0. Definition
 *
 *       Software means the program and related documents which are comprised of those Contribution and licensed under this License.
 *
 *       Contributor means the Individual or Legal Entity who licenses its copyrightable work under this License.
 *
 *       Legal Entity means the entity making a Contribution and all its Affiliates.
 *
 *       Affiliates means entities that control, or are controlled by, or are under common control with a party to this License, ‘control’ means direct or indirect ownership of at least fifty percent (50%) of the voting power, capital or other securities of controlled or commonly controlled entity.
 *
 *    Contribution means the copyrightable work licensed by a particular Contributor under this License.
 *
 *    1. Grant of Copyright License
 *
 *       Subject to the terms and conditions of this License, each Contributor hereby grants to you a perpetual, worldwide, royalty-free, non-exclusive, irrevocable copyright license to reproduce, use, modify, or distribute its Contribution, with modification or not.
 *
 *    2. Grant of Patent License
 *
 *       Subject to the terms and conditions of this License, each Contributor hereby grants to you a perpetual, worldwide, royalty-free, non-exclusive, irrevocable (except for revocation under this Section) patent license to make, have made, use, offer for sale, sell, import or otherwise transfer its Contribution where such patent license is only limited to the patent claims owned or controlled by such Contributor now or in future which will be necessarily infringed by its Contribution alone, or by combination of the Contribution with the Software to which the Contribution was contributed, excluding of any patent claims solely be infringed by your or others’ modification or other combinations. If you or your Affiliates directly or indirectly (including through an agent, patent licensee or assignee）, institute patent litigation (including a cross claim or counterclaim in a litigation) or other patent enforcement activities against any individual or entity by alleging that the Software or any Contribution in it infringes patents, then any patent license granted to you under this License for the Software shall terminate as of the date such litigation or activity is filed or taken.
 *
 *    3. No Trademark License
 *
 *       No trademark license is granted to use the trade names, trademarks, service marks, or product names of Contributor, except as required to fulfill notice requirements in section 4.
 *
 *    4. Distribution Restriction
 *
 *       You may distribute the Software in any medium with or without modification, whether in source or executable forms, provided that you provide recipients with a copy of this License and retain copyright, patent, trademark and disclaimer statements in the Software.
 *
 *    5. Disclaimer of Warranty and Limitation of Liability
 *
 *       The Software and Contribution in it are provided without warranties of any kind, either express or implied. In no event shall any Contributor or copyright holder be liable to you for any damages, including, but not limited to any direct, or indirect, special or consequential damages arising from your use or inability to use the Software or the Contribution in it, no matter how it’s caused or based on which legal theory, even if advised of the possibility of such damages.
 *
 *    End of the Terms and Conditions
 *
 *    How to apply the Mulan Permissive Software License，Version 1 (Mulan PSL v1) to your software
 *
 *       To apply the Mulan PSL v1 to your work, for easy identification by recipients, you are suggested to complete following three steps:
 *
 *       i. Fill in the blanks in following statement, including insert your software name, the year of the first publication of your software, and your name identified as the copyright owner;
 *       ii. Create a file named “LICENSE” which contains the whole context of this License in the first directory of your software package;
 *       iii. Attach the statement to the appropriate annotated syntax at the beginning of each source file.
 *
 *    Copyright (c) [2019] [name of copyright holder]
 *    [Software Name] is licensed under the Mulan PSL v1.
 *    You can use this software according to the terms and conditions of the Mulan PSL v1.
 *    You may obtain a copy of Mulan PSL v1 at:
 *       http://license.coscl.org.cn/MulanPSL
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 *    PURPOSE.
 *
 *    See the Mulan PSL v1 for more details.
 */

package com.zxy.ijplugin.wechat_miniprogram.lang.wxml

import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.impl.source.xml.XmlAttributeValueImpl
import com.intellij.psi.impl.source.xml.XmlTextImpl
import com.intellij.psi.xml.XmlAttribute
import com.zxy.ijplugin.wechat_miniprogram.lang.expr.WxmlJsLanguage
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.utils.isEventHandler
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.utils.valueTextRangeInSelf
import com.zxy.ijplugin.wechat_miniprogram.utils.StringUtils
import com.zxy.ijplugin.wechat_miniprogram.utils.toTextRange

val DOUBLE_BRACE_REGEX = Regex("\\{\\{(.+?)}}", RegexOption.DOT_MATCHES_ALL)

/**
 * 在wxml中的js
 */
class WxmlJSInjector : MultiHostInjector {

  override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> {
    return mutableListOf(XmlTextImpl::class.java, XmlAttributeValueImpl::class.java)
  }

  override fun getLanguagesToInject(multiHostRegistrar: MultiHostRegistrar, psiElement: PsiElement) {
    if (psiElement.language !is WXMLLanguage) return

    when (psiElement) {
      // 普遍的{{}}
      is XmlTextImpl -> {
        injectInText(psiElement, multiHostRegistrar)
      }

      // 在xml属性值中的{{}}
      is XmlAttributeValueImpl -> {
        injectInAttributeValue(psiElement, multiHostRegistrar)
      }
    }
  }

  private fun injectInAttributeValue(
    psiElement: XmlAttributeValueImpl,
    multiHostRegistrar: MultiHostRegistrar
  ) {
    val attribute = psiElement.parent as? XmlAttribute ?: return
    val element = attribute.parent
    val attributeName = attribute.name
    val tagName = element.name
    if (tagName == "wxs" || tagName == "include" || tagName == "import"
      || (tagName == "template" && attributeName == "name")
      || attributeName == "wx:for-item" || attributeName == "wx:key" || attributeName == "wx:for-index"
    ) {
      // wxs等特殊标签 标签的属性不支持 {{}} 语法
      // wx:for 相关的一些辅助属性不支持 {{}} 语法
      return
    }
    if (attribute.isEventHandler() && !DOUBLE_BRACE_REGEX.matches(psiElement.text)) {
      // 此属性是事件
      // 并且属性值中没有双括号
      multiHostRegistrar.startInjecting(WxmlJsLanguage.INSTANCE)
        .addPlace(null, "();", psiElement, psiElement.valueTextRangeInSelf()) // 由于指向一个方法，所以需要在后面加上一个括号
        .doneInjecting()
    } else {
      // 对字符串中的双括号注入js语言
      searchDoubleBraceAndInject(psiElement, multiHostRegistrar, false)
    }
  }

  private fun injectInText(
    psiElement: XmlTextImpl,
    multiHostRegistrar: MultiHostRegistrar
  ) {
    // 对文本中的双括号注入js语言
    searchDoubleBraceAndInject(psiElement, multiHostRegistrar, true)
  }
}

private fun searchDoubleBraceAndInject(
  injectionHost: PsiLanguageInjectionHost,
  multiHostRegistrar: MultiHostRegistrar,
  isTextElement: Boolean
) {
  val inserts = DOUBLE_BRACE_REGEX.findAll(injectionHost.text)
  inserts.forEach {
    val range = it.groups[1]?.range ?: return@forEach

    val text = it.groups[1]?.value!!
    // 处理 &&
    if (isTextElement && tryInjectAndAndMark(range, text, injectionHost, multiHostRegistrar)) {
      return@forEach
    }

    val p = getPrefixAndSuffix(text)
    multiHostRegistrar.startInjecting(WxmlJsLanguage.INSTANCE)
      .addPlace(p.first, p.second, injectionHost, range.toTextRange())
      .doneInjecting()
  }
}

/**
 * 处理 <text>{{ a && b && c }}</text> 这种情况
 * {{ a && b && c }}被当成XmlText处理，但是可能是&是转义字符的缘故，导致无法正确解析(在属性值里面是可以正常解析)
 * 将 {{ a && b && c }} 拆分成 a, b, c 分别注入
 * 注意：无法处理复杂情况 {{ (line.name1 || '') + ((line.name1 && line.name2 ) ? ' ' : '' ) }}
 * FIXME: 有没有其他简单办法？比如禁止&转义
 */
private fun tryInjectAndAndMark(
  range: IntRange, text: String, injectionHost: PsiLanguageInjectionHost,
  multiHostRegistrar: MultiHostRegistrar
): Boolean {
  if (!text.contains("&&")) return false
  // 在括号内的&&不处理
  if (StringUtils.isMarkInsideParenthesis(text, "&&")){
    // 假装已经处理,这样的话表达式会被当成一个普通文本处理,否则&&
    return true
  }

  var idx = 0
  for (s in text.split("&&")) {
    val r = TextRange(range.first + idx, range.first + idx + s.length)
    multiHostRegistrar.startInjecting(WxmlJsLanguage.INSTANCE)
      .addPlace(null, null, injectionHost, r)
      .doneInjecting()
    idx += s.length + 2
  }
  return true
}

private fun getPrefixAndSuffix(text: String): Pair<String?, String?> {
  val trimmedText = text.trim()
  // {{ ...abc }}
  if (text.contains("...")) {
    return Pair("_={", "}")
  }
  // {{ { age: 18 } }}
  if (trimmedText.startsWith("{") && trimmedText.endsWith("}")) {
    return Pair("_=", null)
  }
  // {{ age: 18,name: 'ab:cd' }}
  if (trimmedText.contains(":")) {
    // 查找所有的冒号。如果有一个冒号不在引号内，那么就认为这是一个对象
    // 但是?是三元表达式的条件 aaa ? bbb : ccc 是合法的，不需要转义
    if (StringUtils.containsNoneStringMark(trimmedText, ":") &&
      !StringUtils.containsNoneStringMark(trimmedText, "?")
    ) {
      return Pair("_={", "}")
    }
  }
  return Pair(null, null)
}
