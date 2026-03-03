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
 *       Affiliates means entities that control, or are controlled by, or are under common control with a party to this License, 'control' means direct or indirect ownership of at least fifty percent (50%) of the voting power, capital or other securities of controlled or commonly controlled entity.
 *
 *    Contribution means the copyrightable work licensed by a particular Contributor under this License.
 *
 *    1. Grant of Copyright License
 *
 *       Subject to the terms and conditions of this License, each Contributor hereby grants to you a perpetual, worldwide, royalty-free, non-exclusive, irrevocable copyright license to reproduce, use, modify, or distribute its Contribution, with modification or not.
 *
 *    2. Grant of Patent License
 *
 *       Subject to the terms and conditions of this License, each Contributor hereby grants to you a perpetual, worldwide, royalty-free, non-exclusive, irrevocable (except for revocation under this Section) patent license to make, have made, use, offer for sale, sell, import or otherwise transfer its Contribution where such patent license is only limited to the patent claims owned or controlled by such Contributor now or in future which will be necessarily infringed by its Contribution alone, or by combination of the Contribution with the Software to which the Contribution was contributed, excluding of any patent claims solely be infringed by your or others' modification or other combinations. If you or your Affiliates directly or indirectly (including through an agent, patent licensee or assignee）, institute patent litigation (including a cross claim or counterclaim in a litigation) or other patent enforcement activities against any individual or entity by alleging that the Software or any Contribution in it infringes patents, then any patent license granted to you under this License for the Software shall terminate as of the date such litigation or activity is filed or taken.
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
 *       The Software and Contribution in it are provided without warranties of any kind, either express or implied. In no event shall any Contributor or copyright holder be liable to you for any damages, including, but not limited to any direct, or indirect, special or consequential damages arising from your use or inability to use the Software or the Contribution in it, no matter how it's caused or based on which legal theory, even if advised of the possibility of such damages.
 *
 *    End of the Terms and Conditions
 *
 *    How to apply the Mulan Permissive Software License，Version 1 (Mulan PSL v1) to your software
 *
 *       To apply the Mulan PSL v1 to your work, for easy identification by recipients, you are suggested to complete following three steps:
 *
 *       i. Fill in the blanks in following statement, including insert your software name, the year of the first publication of your software, and your name identified as the copyright owner;
 *       ii. Create a file named "LICENSE" which contains the whole context of this License in the first directory of your software package;
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

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  java
  kotlin("jvm") version "2.1.10"
  id("org.jetbrains.intellij.platform") version "2.6.0"
}

group = "com.inice"
version = "5.3.1"

repositories {
  mavenCentral()
  intellijPlatform {
    defaultRepositories()
  }
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
  implementation("org.jetbrains:annotations-java5:23.0.0")
  implementation("org.jetbrains.kotlin:kotlin-script-runtime:2.0.0")
  implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.0")

  intellijPlatform {
    intellijIdeaUltimate("2025.2")
    bundledPlugin("JavaScript")
    bundledPlugin("com.intellij.modules.json")
    bundledPlugin("com.intellij.css")
    // https://plugins.jetbrains.com/plugin/11451-less
    bundledPlugin("org.jetbrains.plugins.less")
    //plugins.jetbrains.com/plugin/11449-sass
    bundledPlugin("org.jetbrains.plugins.sass")
    // https://plugins.jetbrains.com/plugin/7316-stylus
    plugin("org.jetbrains.plugins.stylus", "252.23892.298")
  }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
intellijPlatform {
  pluginConfiguration {
    ideaVersion {
      sinceBuild = "252"
      untilBuild = "253.*"
    }
  }
  pluginVerification {
    ides {
      recommended()
    }
  }
}

tasks {
  withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = JvmTarget.JVM_17
  }
  publishPlugin {
    token.set(System.getenv("TOKEN"))
  }
  patchPluginXml {
    val changeNotes = File(rootProject.projectDir, "changeNotes.html").readText(Charsets.UTF_8)
    val pluginDescription = File(rootProject.projectDir, "pluginDescription.html").readText(Charsets.UTF_8)
    this.changeNotes.set(changeNotes)
    this.pluginDescription.set(pluginDescription)
  }
}

sourceSets {
  main {
    java {
      srcDirs("src")
      srcDirs("gen")
    }
    resources {
      srcDirs("resources")
    }
  }
}
