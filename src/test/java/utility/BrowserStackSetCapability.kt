package utility

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader

object BrowserStackSetCapability {

    private val parser = JSONParser()

            private val `object`: Any = parser.parse(
                FileReader(
                    "./BrowserStackSetCapability"
                )
            )
            private val jsonObject = `object` as JSONObject

            val os_version = jsonObject ["os_version"] as String?
            val resolution = jsonObject ["resolution"] as String?
            val browser = jsonObject ["browser"] as String?
            val browser_version = jsonObject ["browser_version"] as String?
            val os = jsonObject ["os"] as String?

}




