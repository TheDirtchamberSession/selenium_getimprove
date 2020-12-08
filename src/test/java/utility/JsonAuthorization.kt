package utility

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader

object JsonAuthorization {

    private val parser = JSONParser()

            private val `object`: Any = parser.parse(
                FileReader(
                    "./json"
                )
            )
            private val jsonObject = `object` as JSONObject



}




