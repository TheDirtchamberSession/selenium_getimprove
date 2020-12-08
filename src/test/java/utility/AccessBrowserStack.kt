package utility

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader

object AccessBrowserStack {

    private val parser = JSONParser()

            private val `object`: Any = parser.parse(
                FileReader(
                    "./AccessBrowserStack"
                )
            )
            private val jsonObject = `object` as JSONObject

            val automate_USERNAME = jsonObject ["automate_USERNAME"] as String?
            val automate_ACCESS_KEY = jsonObject ["automate_ACCESS_KEY"] as String?

}




