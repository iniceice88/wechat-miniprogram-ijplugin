import com.intellij.json.psi.JsonElement
import com.intellij.json.psi.JsonFile
import com.intellij.json.psi.JsonObject
import com.intellij.json.psi.JsonStringLiteral
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.zxy.ijplugin.wechat_miniprogram.utils.JsonElementUtils
import com.zxy.ijplugin.wechat_miniprogram.utils.JsonElementUtils.findTargetJsonObject
import org.junit.Test

class AppJsonTest : BasePlatformTestCase() {

  override fun getTestDataPath(): String {
    return "src/test/testData"
  }

  @Test
  fun testAppJson() {
    // 配置测试文件
    myFixture.configureByFile("app.json")

    // 获取 PsiFile 对象并确保其为 JsonFile
    val jsonFile = myFixture.file as JsonFile

    // 获取 JSON 的根元素
    val rootElement = jsonFile.topLevelValue ?: return

    var targetObject = findTargetJsonObject(rootElement, "\"order/pages/orders/index\"")
    assertNotNull(targetObject)
    println(JsonElementUtils.getPathToRoot(targetObject as JsonElement).joinToString("/"))
    var match = JsonElementUtils.isParentMatch(targetObject, "[]/pages/./[]/subpackages/./$".split("/"))
    assertTrue(match)

    val subPkgEle = targetObject.parent.parent.parent as? JsonObject

    if (subPkgEle != null) {

      subPkgEle.propertyList.find { it.name == "root" }?.value?.let {
        it as JsonStringLiteral
        val text = it.value
        println(text)
      }
    }

    targetObject = findTargetJsonObject(rootElement, "\"p0/tab-bar/pages/tab2/index\"")
    println(JsonElementUtils.getPathToRoot(targetObject as JsonElement).joinToString("/"))
    match = JsonElementUtils.isParentMatch(targetObject, "[]/pages/./$".split("/"))
    assertTrue(match)

    targetObject = findTargetJsonObject(rootElement, "\"img/tab/tab1.png\"")
    println(JsonElementUtils.getPathToRoot(targetObject as JsonElement).joinToString("/"))
    match = JsonElementUtils.isParentMatch(targetObject, "iconPath/./[]/list/./tabBar/./$".split("/"))
    assertTrue(match)

    targetObject = findTargetJsonObject(rootElement, "\"miniprogram_npm/@vant/weapp/divider/index\"")
    // van-divider/./usingComponents/./$
    println(JsonElementUtils.getPathToRoot(targetObject as JsonElement).joinToString("/"))
  }
}
