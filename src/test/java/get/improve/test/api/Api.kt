package get.improve.test.api

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.testng.annotations.Test

class Api {


    @Test(priority = 1, description = "Bad Request_400")
    fun badRequest_400() {

        val request = RestAssured.given()
            .headers("Accept", ContentType.JSON)

    }
}

