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

import com.intellij.json.psi.*
import com.intellij.openapi.diagnostic.Logger
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.impl.source.xml.TagNameReference
import com.intellij.psi.xml.XmlTag
import com.intellij.util.ProcessingContext
import com.zxy.ijplugin.wechat_miniprogram.context.RelateFileHolder
import com.zxy.ijplugin.wechat_miniprogram.utils.JsonElementUtils
import com.zxy.ijplugin.wechat_miniprogram.utils.findChildrenOfType

/**
 * app.json 和 page.json 中的引用
 * app.json中定义了全局组件和页面路径
 * page.json中通过usingComponents定义了组路径
 */
class JsonReferenceContributor : PsiReferenceContributor() {
  override fun registerReferenceProviders(psiReferenceRegistrar: PsiReferenceRegistrar) {
    // 注册组件json中usingComponents中组件名称引用到wxml中的组件标签
    psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement(JsonProperty::class.java),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
          element as JsonProperty
          // 必须要有对应的wxml文件
          val wxmlPsiFile = RelateFileHolder.MARKUP.findFile(element.containingFile) ?: return PsiReference.EMPTY_ARRAY

          // 父级必须是usingComponents
          if (!JsonElementUtils.isParentMatch(element, "./usingComponents/./$".split("/"))) {
            return PsiReference.EMPTY_ARRAY
          }

          if (wxmlPsiFile.findChildrenOfType<XmlTag>().filter { it.name == element.name }.any { xmlTag ->
              xmlTag.references.any {
                it is TagNameReference && it.resolve() == element
              }
            }) {
            return arrayOf(JsonRegistrationReference(element))
          }
          return PsiReference.EMPTY_ARRAY
        }
      })

    // 小程序个页面的json配置文件中的usingComponents配置
    psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement(JsonStringLiteral::class.java),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          psiElement: PsiElement,
          processingContext: ProcessingContext
        ): Array<out PsiReference> {
          psiElement as JsonStringLiteral

          // 必须要有对应的wxml文件
          RelateFileHolder.MARKUP.findFile(psiElement.containingFile) ?: return PsiReference.EMPTY_ARRAY

          // usingComponents
          if (JsonElementUtils.isParentMatch(psiElement, "././usingComponents/./$".split("/"))) {
            return ComponentFileReferenceSet(psiElement).allReferences
          }

          // componentGenerics
          // https://developers.weixin.qq.com/miniprogram/dev/framework/custom-component/generics.html#%E6%8A%BD%E8%B1%A1%E8%8A%82%E7%82%B9%E7%9A%84%E9%BB%98%E8%AE%A4%E7%BB%84%E4%BB%B6
          if (JsonElementUtils.isParentMatch(psiElement, "././././componentGenerics/./$".split("/"))) {
            return ComponentFileReferenceSet(psiElement).allReferences
          }
          return PsiReference.EMPTY_ARRAY
        }
      }
    )

    // 小程序的app.json配置文件中的pages配置项
    // 解析被注册的page的路径
    // 1. pages
    // 2. usingComponents
    // 3. subpackages/pages
    // 4. tabBar/list[pagePath]
    // 5. entryPagePath
    psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement(JsonStringLiteral::class.java),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(
          psiElement: PsiElement, processingContext: ProcessingContext
        ): Array<out PsiReference> {
          if (RelateFileHolder.JSON.findAppFile(psiElement.project) != psiElement.containingFile.originalFile)
            return PsiReference.EMPTY_ARRAY

          psiElement as JsonStringLiteral

          // entryPagePath
          if (JsonElementUtils.isParentMatch(psiElement, "entryPagePath/./$".split("/"))) {
            return ComponentFileReferenceSet(psiElement).allReferences
          }

          // pages
          if (JsonElementUtils.isParentMatch(psiElement, "[]/pages/./$".split("/"))) {
            return ComponentFileReferenceSet(psiElement).allReferences
          }
          // usingComponents
          if (JsonElementUtils.isParentMatch(psiElement, "././usingComponents/./$".split("/"))) {
            return ComponentFileReferenceSet(psiElement).allReferences
          }
          // subpackages
          if (JsonElementUtils.isParentMatch(psiElement, "[]/pages/./[]/subpackages/./$".split("/"))) {
            val subPkgEle = psiElement.parent.parent.parent as? JsonObject

            // subpackages下pages是相对root的路径,需要找到root的值拼成完整路径
            if (subPkgEle != null) {
              subPkgEle.propertyList.find { it.name == "root" }?.value?.let {
                it as JsonStringLiteral
                // it.text是包含双引号的
                val cs = SubPackageFileReferenceSet(psiElement, it.value)
                return cs.allReferences
              }
            }
          }
          // tabBar
          if (JsonElementUtils.isParentMatch(psiElement, "pagePath/./[]/list/./tabBar/./$".split("/"))) {
            return ComponentFileReferenceSet(psiElement).allReferences
          }

          return PsiReference.EMPTY_ARRAY
        }
      })
  }
}

class JsonRegistrationReference(jsonProperty: JsonProperty) :
  PsiPolyVariantReferenceBase<JsonProperty>(jsonProperty, jsonProperty.nameElement.textRangeInParent) {
  override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
    val wxmlPsiFile = RelateFileHolder.MARKUP.findFile(element.containingFile) ?: return ResolveResult.EMPTY_ARRAY
    return wxmlPsiFile.findChildrenOfType<XmlTag>().filter {
      it.name == element.name
    }.filter { xmlTag ->
      xmlTag.references.any {
        it is TagNameReference && it.resolve() == element
      }
    }.map {
      PsiElementResolveResult(it)
    }.toTypedArray()
  }

}

