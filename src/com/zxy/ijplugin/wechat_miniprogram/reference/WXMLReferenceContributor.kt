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

package com.zxy.ijplugin.wechat_miniprogram.reference

import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.XmlPatterns
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet
import com.intellij.psi.util.elementType
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlAttributeValue
import com.intellij.psi.xml.XmlTokenType
import com.intellij.util.ProcessingContext
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.WXMLLanguage
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.WXMLPsiFile
import com.zxy.ijplugin.wechat_miniprogram.utils.ComponentWxmlUtils
import com.zxy.ijplugin.wechat_miniprogram.utils.toTextRange

class WXMLReferenceContributor : PsiReferenceContributor() {

  companion object {
    // 描述可以被解析为路径的元素的属性
    private val PATH_ATTRIBUTES = arrayOf(
      PathAttribute("wxs", "src"),
      PathAttribute("image", "src", true),
      PathAttribute("import", "src"),
      PathAttribute("include", "src"),
      PathAttribute("cover-image", "src", true)
    )

    private fun findPathAttribute(tagName: String, attributeName: String): PathAttribute? {
      return PATH_ATTRIBUTES.find {
        it.tagName == tagName && it.attributeName == attributeName
      }
    }
  }

  override fun registerReferenceProviders(psiReferenceRegistrar: PsiReferenceRegistrar) {
    // 解析wxml中的id
    psiReferenceRegistrar.registerReferenceProvider(
      XmlPatterns.xmlAttributeValue().withLocalName("id").withLanguage(WXMLLanguage.INSTANCE),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          psiElement: PsiElement, p1: ProcessingContext
        ): Array<PsiReference> {
          if (psiElement is XmlAttributeValue) {
            val valueTokens = psiElement.children.filter {
              it.elementType == XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN
            }

            if (valueTokens.size == 1) {
              // 这个字符串内容必须在 id中 并且只有一个ValueToken
              // 没有双括号
              return arrayOf(WXMLIdReference(psiElement, valueTokens[0].textRangeInParent))
            }
          }
          return PsiReference.EMPTY_ARRAY
        }
      }
    )

    // 解析wxml中的class
    psiReferenceRegistrar.registerReferenceProvider(
      XmlPatterns.xmlAttributeValue().withLanguage(WXMLLanguage.INSTANCE)
        .inFile(PlatformPatterns.psiFile(WXMLPsiFile::class.java)),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          psiElement: PsiElement, p1: ProcessingContext
        ): Array<PsiReference> {
          psiElement is XmlAttributeValue
          val attribute = psiElement.parent as? XmlAttribute
          if (attribute != null && (attribute.name == "class" || ComponentWxmlUtils.isExternalClassesAttribute(
              attribute
            ))
          ) {
            val findResults = Regex("[_\\-a-zA-Z][_\\-a-zA-Z0-9]+").findAll(psiElement.text)
            return findResults.map { matchResult ->
              WXMLClassReference(
                psiElement, matchResult.range.toTextRange()
              )
            }.toList().toTypedArray()
          }
          return PsiReference.EMPTY_ARRAY
        }
      }
    )

    // 解析wxml中的路径属性(指向另一个文件)
    psiReferenceRegistrar.registerReferenceProvider(
      XmlPatterns.xmlAttributeValue().withLanguage(WXMLLanguage.INSTANCE),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          psiElement: PsiElement, processingContext: ProcessingContext
        ): Array<out PsiReference> {
          psiElement is XmlAttributeValue
          val attribute = psiElement.parent as? XmlAttribute ?: return PsiReference.EMPTY_ARRAY
          val tag = attribute.parent
          val pathAttribute = findPathAttribute(tag.name, attribute.name)
          if (pathAttribute != null) {
            // 这个属性是可解析为路径的
            return object : FileReferenceSet(psiElement) {
              override fun isSoft(): Boolean {
                return pathAttribute.isSoftReference
              }
            }.allReferences
          }
          return PsiReference.EMPTY_ARRAY
        }
      }
    )

    // 解析wxml中的template.is属性
    psiReferenceRegistrar.registerReferenceProvider(
      XmlPatterns.xmlAttributeValue()
        .withLanguage(WXMLLanguage.INSTANCE)
        .withLocalName("is")
        .withSuperParent(2, XmlPatterns.xmlTag().withLocalName("template")),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          psiElement: PsiElement, p1: ProcessingContext
        ): Array<PsiReference> {
          psiElement as XmlAttributeValue
          return arrayOf(WXMLTemplateIsAttributeReference(psiElement))
        }
      }
    )

    // 解析wxml中的template.name属性
    psiReferenceRegistrar.registerReferenceProvider(
      XmlPatterns.xmlAttributeValue()
        .withLanguage(WXMLLanguage.INSTANCE)
        .withLocalName("name")
        .withSuperParent(2, XmlPatterns.xmlTag().withLocalName("template")),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          psiElement: PsiElement, p1: ProcessingContext
        ): Array<PsiReference> {
          psiElement as XmlAttributeValue
          return arrayOf(WXMLTemplateNameAttributeReference(psiElement))
        }
      }
    )

    // 解析wxml元素的slot属性,Ctrl+点击slot名称跳转到对应的slot
    psiReferenceRegistrar.registerReferenceProvider(
      XmlPatterns.xmlAttributeValue()
        .withLanguage(WXMLLanguage.INSTANCE)
        .withLocalName("slot"),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          psiElement: PsiElement, p1: ProcessingContext
        ): Array<PsiReference> {
          psiElement as XmlAttributeValue
          val wxmlAttribute = psiElement.parent as? XmlAttribute
          if (wxmlAttribute != null) {
            return arrayOf(WXMLNamedSlotReference(psiElement))
          }
          return PsiReference.EMPTY_ARRAY
        }
      }
    )

    // 双向绑定
    psiReferenceRegistrar.registerReferenceProvider(
      XmlPatterns.xmlAttribute().withLanguage(WXMLLanguage.INSTANCE),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          element: PsiElement, context: ProcessingContext
        ): Array<PsiReference> {
          element as XmlAttribute
          if (element.name.startsWith("model:")) {
            return arrayOf(MarkupTwoWayBindingReference(element))
          }
          return PsiReference.EMPTY_ARRAY
        }
      }
    )
  }

}
