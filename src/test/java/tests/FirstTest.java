package tests;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class FirstTest {
 /*   private static WebDriver driver;

   @BeforeAll
    public static void setUp()

    {
        driver = new ChromeDriver();
    } */

    @Test
    public void testAdd() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        String url = "http://localhost:8080/food";
        driver.get(url);

        //Первый шаг
        WebElement add = driver.findElement(By.xpath("//button[text()=\"Добавить\"]"));
        add.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        WebElement name = driver.findElement(By.id("name"));
        name.sendKeys("Банан");
        WebElement type = driver.findElement(By.id("type"));
        type.click();
        WebElement typeFruit = type.findElement(By.xpath("//option[text()=\"Фрукт\"]"));
        typeFruit.click();
        WebElement checkbox = driver.findElement(By.id("exotic"));
        if (checkbox.isSelected()) {
            checkbox.click();
        }

        WebElement save = driver.findElement(By.id("save"));
        save.click();

        //Второй шаг
        WebElement add2 = driver.findElement(By.xpath("//button[text()=\"Добавить\"]"));
        add2.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        WebElement name2 = driver.findElement(By.id("name"));
        name2.sendKeys("Питайя");
        WebElement type2 = driver.findElement(By.id("type"));
        type2.click();
        WebElement typeFruit2 = type2.findElement(By.xpath("//option[text()=\"Фрукт\"]"));
        typeFruit2.click();
        WebElement checkbox2 = driver.findElement(By.id("exotic"));
        if (!checkbox2.isSelected()) {
            checkbox2.click();
        }

        WebElement save2 = driver.findElement(By.id("save"));
        save2.click();

        Thread th = new Thread();  // очень интересно почему именно тут нужно поспать
        th.sleep(4);

        //Третий шаг
        WebElement add3 = driver.findElement(By.xpath("//button[text()=\"Добавить\"]"));
        add3.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        WebElement name3 = driver.findElement(By.id("name"));
        name3.sendKeys("Огурец");
        WebElement type3 = driver.findElement(By.id("type"));
        type3.click();
        WebElement typeFruit3 = type3.findElement(By.xpath("//option[text()=\"Овощ\"]"));
        typeFruit3.click();
        WebElement checkbox3 = driver.findElement(By.id("exotic"));
        if (checkbox3.isSelected()) {
            checkbox3.click();
        }

        WebElement save3 = driver.findElement(By.id("save"));
        save3.click();


        //Четвертый шаг
        WebElement add4 = driver.findElement(By.xpath("//button[text()=\"Добавить\"]"));
        add4.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        WebElement name4 = driver.findElement(By.id("name"));
        name4.sendKeys("Патиссон");
        WebElement type4 = driver.findElement(By.id("type"));
        type4.click();
        WebElement typeFruit4 = type4.findElement(By.xpath("//option[text()=\"Овощ\"]"));
        typeFruit4.click();
        WebElement checkbox4 = driver.findElement(By.id("exotic"));
        if (!checkbox4.isSelected()) {
            checkbox4.click();
        }

        WebElement save4 = driver.findElement(By.id("save"));
        save4.click();

        th.sleep(4);

        //Сброс
        WebElement sandbox = driver.findElement(By.id("navbarDropdown"));
        sandbox.click();
        WebElement reset = driver.findElement(By.id("reset"));
        reset.click();

        driver.quit();
    }
}