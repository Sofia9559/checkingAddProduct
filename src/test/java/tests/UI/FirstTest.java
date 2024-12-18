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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.util.Properties;

@DisplayName("Мой первый UI тест")
public class FirstTest {
    private static WebDriver driver;
    Properties properties;
    {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(new File("src/test/resources/application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Проверка добавления товара в корзину")
    @Description("Поочередно добавим в корзину фрукты и овощи, активируем и деактивируем чекбокс Экзотический")
    @Story("Story1")
    @Owner("Третьякова Софья")
    @Test
    public void testAdd() {

        initDriver();
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

    private void initDriver(){
        loadCustomProperties();
        if ("remote".equalsIgnoreCase(properties.getProperty("type.driver"))){
            initRemoteDriver();
        } else {
            driver = new ChromeDriver();
        }
        driver.get(properties.getProperty("base.url"));
    }

    private void initRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(properties.getProperty("type.browser"));
        capabilities.setVersion("109.0");
        capabilities.setCapability("se:enableVNC", true);
        capabilities.setCapability("se:enableVideo", false);
        try {
            driver = new RemoteWebDriver(URI.create(properties.getProperty("selenoid.url")).toURL(),capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод подгружает содержимого файла application.properties в переменную {@link #properties}
     * Либо из файла переданного пользователем через настройку -DpropFile={nameFile}
     */
    private void loadApplicationProperties() {
        try {
            properties.load(new FileInputStream(
                    new File("src/test/resources/" +
                            System.getProperty("propFile", "application") + ".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод заменяет значение содержащиеся в ключах переменной {@link #properties}
     * Заменяет на те значения, что передал пользователь через maven '-D{name.key}={value.key}'
     * Замена будет происходить только в том случае если пользователь передаст совпадающий key из application.properties
     */
    private void loadCustomProperties() {
        properties.forEach((key, value) -> System.getProperties()
                .forEach((customUserKey, customUserValue) -> {
                    if (key.toString().equals(customUserKey.toString()) &&
                            !value.toString().equals(customUserValue.toString())) {
                        properties.setProperty(key.toString(), customUserValue.toString());
                    }
                }));
    }
}