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

@DisplayName("Мой первый UI тест")
public class FirstTest {
    private static WebDriver driver;

    @DisplayName("Проверка добавления товара в корзину")
    @Description("Поочередно добавим в корзину фрукты и овощи, активируем и деактивируем чекбокс Экзотический")
    @Story("Story1")
    @Owner("Третьякова Софья")
    @Test
    public void testAdd() throws InterruptedException {
        driver = new ChromeDriver();
        String url = "http://localhost:8080/food";
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        addItem("Банан", "Фрукт", "true");
        addItem("Питайя", "Фрукт", "false");
        addItem("Огурец", "Овощ", "true");
        addItem("Патиссон", "Овощ", "false");

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
}