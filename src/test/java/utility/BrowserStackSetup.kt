package utility

import org.apache.commons.io.FileUtils
import org.apache.commons.lang.RandomStringUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeMethod
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit


open class BrowserStackSetup {

    lateinit var driver: WebDriver
    lateinit var waitAnd: WaitAnd
    lateinit var wait: WebDriverWait

    @BeforeMethod(alwaysRun = true)
    fun setUp() {

        val url = "https://${AccessBrowserStack.automate_USERNAME}:${AccessBrowserStack.automate_ACCESS_KEY}@hub-cloud.browserstack.com/wd/hub"
        val caps = DesiredCapabilities()

        caps.setCapability("os_version", "${BrowserStackSetCapability.os_version}")
        caps.setCapability("resolution", "${BrowserStackSetCapability.resolution}")
        caps.setCapability("browser", "${BrowserStackSetCapability.browser}")
        caps.setCapability("browser_version", "${BrowserStackSetCapability.browser_version}")
        caps.setCapability("os", "${BrowserStackSetCapability.os}")

        driver = RemoteWebDriver(URL(url), caps)
        wait = WebDriverWait(driver, 25)
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS)
        waitAnd = WaitAnd(wait)
        driver.get("")

        }

    @AfterMethod
    @Throws(IOException::class)
    open fun takeScreenShotOnFailure(testResult: ITestResult) {

        val prefix = "Error_"
        val errorElement = prefix + RandomStringUtils.randomAlphabetic(5).toLowerCase()
        if (testResult.status == ITestResult.FAILURE) {
            val scrFile = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
            FileUtils.copyFile(scrFile, File("./src/test/resources/ScreenshotsFailure/$errorElement.png"))
        }
    }

    @AfterTest
    fun endTest(){
        driver.quit()
    }
}
