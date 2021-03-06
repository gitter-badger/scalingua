/******************************************************************************
 * Copyright © 2016 Maxim Karpov                                              *
 *                                                                            *
 * Licensed under the Apache License, Version 2.0 (the "License");            *
 * you may not use this file except in compliance with the License.           *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 *     http://www.apache.org/licenses/LICENSE-2.0                             *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/

package ru.makkarpov.scalingua.extract

import java.io.File

import ru.makkarpov.scalingua.Compat._

object ExtractorSettings {
  val SettingsPrefix = "scalingua:"

  def fromContext(c: Context): ExtractorSettings = {
    val setts = c.settings
      .filter(_.startsWith(SettingsPrefix))
      .map(_.substring(SettingsPrefix.length).split("=", 2))
      .map{
        case Array(k, v) => k -> v
        case Array(x) => c.abort(c.enclosingPosition, s"Invalid setting: `$x`")
      }.toMap

    if (!setts.contains("target")) ExtractorSettings(enable = false, "", new File("messages.pot"))
    else ExtractorSettings(enable = true, setts.getOrElse("baseDir", ""), new File(setts("target")))
  }
}

case class ExtractorSettings(enable: Boolean, srcBaseDir: String, targetFile: File)
