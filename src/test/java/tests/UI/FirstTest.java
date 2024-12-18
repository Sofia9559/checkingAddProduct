package tests.UI;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;

@DisplayName("Мой первый UI тест")
public class FirstTest {
    private static WebDriver driver;
    private Properties properties = new Properties();


    @DisplayName("Проверка добавления товара в корзину")
    @Description("Поочередно добавим в корзину фрукты и овощи, активируем и деактивируем чекбокс Экзотический")
    @Story("Story1")
    @Owner("Третьякова Софья")
    @Test
    public void testAdd() {
        driver = new ChromeDriver();
        String url = "https://qualit.applineselenoid.fvds.ru/food";
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        addItem("Банан", "Фрукт", "true");
        verifyItemAdded("Банан");
        addItem("Питайя", "Фрукт", "false");
        verifyItemAdded("Питайя");
        addItem("Огурец", "Овощ", "true");
        verifyItemAdded("Огурец");
        addItem("Патиссон", "Овощ", "false");
        verifyItemAdded("Патиссон");

        // Сброс
        wait.until(ExpectedConditions.elementToBeClickable(By.id("navbarDropdown"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("reset"))).click();

        driver.quit();
    }

    private void addItem(String name, String type, String exotic) {
        boolean exoticValue = Boolean.parseBoolean(exotic);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=\"Добавить\"]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(name);
        WebElement typeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("type")));
        typeDropdown.click();
        typeDropdown.findElement(By.xpath("//option[text()=\"" + type + "\"]")).click();

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("exotic")));
        if (checkbox.isSelected() != exoticValue) {
            checkbox.click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id("save"))).click();
    }

    private void verifyItemAdded(String itemName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isItemPresent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='" + itemName + "']"))) != null;
        assert isItemPresent : "Элемент " + itemName + " не был добавлен.";
    }
}