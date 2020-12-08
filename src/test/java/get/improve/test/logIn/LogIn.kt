package get.improve.test.logIn

import org.testng.annotations.Test
import utility.BeforeAllUser

class LogIn : BeforeAllUser(){

    @Test(description = "Valid LogIn Test")
    fun logIn(){

      val myUrl =  driver.currentUrl
        println(myUrl)

    }
}
