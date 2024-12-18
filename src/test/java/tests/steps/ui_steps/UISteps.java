package tests.steps.ui_steps;

import io.cucumber.java.ru.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UISteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @Допустим("откроем страницу добавления продукта")
    @Step("Открытие страницы добавления продукта")
    public void openAddProductPage() {
        driver = new ChromeDriver();
        driver.get("https://qualit.applineselenoid.fvds.ru/food");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Тогда("добавим продукт {string} типа {string} и установим Exotic в {string}")
    @Step("Добавление продукта {name} типа {type} с exotic = {exotic}")
    public void addItem(String name, String type, String exotic) {
        boolean exoticValue = Boolean.parseBoolean(exotic);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=\"Добавить\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(name);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("type"))).click();
        driver.findElement(By.xpath("//option[text()=\"" + type + "\"]")).click();
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("exotic")));
        if (checkbox.isSelected() != exoticValue) {
            checkbox.click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id("save"))).click();
    }

    @И("проверим что продукт {string} добавлен в таблицу")
    @Step("Проверка что продукт добавлен в корзину")
    public void verifyItemAdded(String itemName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isItemPresent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='" + itemName + "']"))) != null;
        assert isItemPresent : "Элемент " + itemName + " не был добавлен.";
    }

    @Затем("все продукты должны быть сброшены")
    @Step("Сброс продуктов")
    public void resetProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("navbarDropdown"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("reset"))).click();
        driver.quit();
    }
}
