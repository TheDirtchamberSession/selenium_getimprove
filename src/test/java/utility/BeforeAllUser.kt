package utility

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest

open class BeforeAllUser {

    lateinit var waitAnd: WaitAnd
    lateinit var driver: WebDriver
    lateinit var wait: WebDriverWait
    var firefoxOptions = FirefoxOptions()
    var chromeOptions = ChromeOptions()

    @BeforeTest
    open fun setUp() {

        // firefoxOptions.setHeadless(true)
        // chromeOptions.addArguments("headless", "--window-size=1920x1080", "--no-sandbox")
        
        driver = ChromeDriver(chromeOptions)
        driver.manage().window().maximize()
        driver.get("https://app.stg.getimprove.com/")
        wait = WebDriverWait(driver, 15)
        waitAnd = WaitAnd(wait)
    }


    @AfterTest
    fun endDriver() {
        driver.quit()
    }
}