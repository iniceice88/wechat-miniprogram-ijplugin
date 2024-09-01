
import com.intellij.json.psi.JsonElement
import com.intellij.json.psi.JsonFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.zxy.ijplugin.wechat_miniprogram.utils.JsonElementUtils
import com.zxy.ijplugin.wechat_miniprogram.utils.JsonElementUtils.findTargetJsonObject
import org.junit.Test

class PageJsonTest : BasePlatformTestCase() {

  override fun getTestDataPath(): String {
    return "src/test/testData"
  }

  @Test
  fun testAppJson() {
    // 配置测试文件
    myFixture.configureByFile("index.json")

    // 获取 PsiFile 对象并确保其为 JsonFile
    val jsonFile = myFixture.file as JsonFile

    // 获取 JSON 的根元素
    val rootElement = jsonFile.topLevelValue ?: return

    var targetObject = findTargetJsonObject(rootElement, "\"../order-line/index\"")
    assertNotNull(targetObject)
    println(JsonElementUtils.getPathToRoot(targetObject as JsonElement).joinToString("/"))

  }
}
