package utility

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class WaitAnd (var wait: WebDriverWait){

    fun click (locator: By) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click()
    }

    fun clear (locator: By) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).clear()
    }
}