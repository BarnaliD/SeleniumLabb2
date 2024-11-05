import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Assertions;
import java.util.List;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MySeleniumTest {
    private static WebDriver driver;

    @BeforeAll
    static void setup() {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origin=*");
        option.addArguments("incognito");
        driver = new ChromeDriver(option);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // Set implicit wait globally
    }

    @BeforeEach
    public void navigate() {
        driver.get("https://www.iths.se");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // Wait until the cookie consent button is visible and clickable
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll']")));
            cookieBtn.click();
            System.out.println("Cookie consent accepted.");
        } catch (Exception e) {
            System.out.println("Cookie consent banner not found or already dismissed: " + e.getMessage());
        }
        driver.manage().window().maximize();  // Maximize window at startup
    }



    @Test
    public void checkWebsiteTitle() {
        String websiteTitle = driver.getTitle();
        System.out.println("titeln på ITHS.se: " + websiteTitle);
        assertEquals("IT-Högskolan – Här startar din IT-karriär!", websiteTitle);
        System.out.println(" Assertions passed successfully , Title matches!");
    }

    @Test
    public void verifyPhoneNumber() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Scroll down using java script executor
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        WebElement phoneLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='callto:031-790 42 55']")));
        Assertions.assertTrue(phoneLink.isDisplayed(), "Phone link is not displayed");

        String hrefValue = phoneLink.getAttribute("href");
        Assertions.assertEquals("callto:031-790 42 55", hrefValue, "Phone number link is incorrect");

        String linkText = phoneLink.getText();
        Assertions.assertEquals("031-790 42 55", linkText.trim(), "Phone number text is incorrect");

        System.out.println("All assertions passed successfully!");
    }

    @Test
    public void userClicksOnMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String expectedText = "UTBILDNINGAR";  // Define the expected text for the assertion
        // Minimize the screen for this test (simulate mobile or tablet size)
        driver.manage().window().setSize(new Dimension(375, 812));  // iPhone 12 screen dimensions as an example


        try {
            // Wait until the menu button is clickable and then click it
            WebElement menuElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fa fa-bars']")));
            menuElement.click();
            System.out.println("Menu button clicked successfully.");
        } catch (Exception e) {
            System.out.println("Failed to click the menu button: " + e.getMessage());
        }

        try {
            // Wait until the first menu item is visible and get its text
            WebElement firstMenuItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='nav-utbildningar']")));
            String actualText = firstMenuItem.getText().trim();
            Assertions.assertEquals(expectedText, actualText, "First menu item text does  match the expected text.");
            System.out.println("Menu item text matches expected text.");
        } catch (Exception e) {
            System.out.println("Failed to find or validate the menu item text: " + e.getMessage());
        }
    }


   /* @Test
    public void checkUtbildningGöteborg()throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement utbildningarLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"nav-utbildningar\"]/a")));
        utbildningarLink.click();
        Thread.sleep(10000);
        //WebElement göteborgLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"course-filter-bar\"]/div/div[1]/a[3]")));
        WebElement göteborgLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-utbildningar")));
        göteborgLink.click();

        WebElement pageHeader = driver.findElement(By.xpath("//h1[normalize-space()='IT-utbildningar Göteborg']"));
        String actualText = pageHeader.getText().trim();
        String expectedText = "IT-utbildningar Göteborg";
        Assertions.assertEquals(expectedText, actualText);
    }*/

    /*@Test
    public void checkUtbildningStockholm() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement utbildningarLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"nav-utbildningar\"]/a")));
        utbildningarLink.click();
        Thread.sleep(10000);
        WebElement stockholmLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"course-filter-bar\"]/div/div[1]/a[2]")));
        //Thread.sleep(10000);
        stockholmLink.click();

        WebElement pageHeader = driver.findElement(By.xpath("//h1[normalize-space()='IT-utbildningar Stockholm']"));
        String actualText = pageHeader.getText().trim();
        String expectedText = "IT-utbildningar Stockholm";
        Assertions.assertEquals(expectedText, actualText);
        System.out.println("All assertions passed successfully!");
    }*/

    @Test
    public void checkKontakt() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement kontaktLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-kontakt")));
        kontaktLink.click();

        WebElement contactDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.card-employees__email")));
        Assertions.assertTrue(contactDiv.isDisplayed(), "Contact div should be visible after scrolling.");

        String expectedEmail = "marcus@iths.se";
        Assertions.assertEquals(expectedEmail, contactDiv.getText().trim(), "Email text should match.");

        WebElement phoneLink = driver.findElement(By.cssSelector("a.card-employees__phone"));
        String expectedPhone = "070-5169513";
        Assertions.assertEquals(expectedPhone, phoneLink.getText().trim(), "Phone number should match.");
        System.out.println("All assertions passed successfully! Name and Phone Number matches");
    }

    @Test
    public void checkLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='https://www.ithsdistans.se/login/index.php']")));
        loginLink.click();

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameField.sendKeys("Test.Testson@iths.se");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Testing123");

        WebElement loginButton = driver.findElement(By.className("loginbtn"));
        loginButton.click();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginerrormessage")));
        String alertMessage = errorMessage.getText().trim();
        Assertions.assertEquals("Invalid login, please try again", alertMessage);
        System.out.println("All assertions passed successfully Message with Invalid login appears !");
    }

    @Test
    public void checkNews() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement nyheterLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-nyheter a")));
        nyheterLink.click();

        wait.until(ExpectedConditions.urlContains("/nyheter/"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        //WebElement scrumMasterTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"content\"]/div/div[4]/a/div[2]/h6")));
        WebElement scrumMasterTitle = driver.findElement(By.cssSelector("#content > div > div:nth-child(5) > a > div.card__content > h6"));


        js.executeScript("arguments[0].scrollIntoView(true);", scrumMasterTitle);
        System.out.println(scrumMasterTitle.getText());

        String actualText = (String) js.executeScript("return arguments[0].innerText;", scrumMasterTitle);
        String expectedText = "Scrum Master Lön";
        Assertions.assertEquals(actualText.trim(), expectedText, "The title matches!");
        System.out.println("All assertions passed successfully!Title matches");
    }

    @Test
    public void checkItShop() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement ItShopLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-ithshop")));
        ItShopLink.click();

        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.cky-banner-btn-close")));
        closeButton.click();

        WebElement hoodiesElement = driver.findElement(By.xpath("//h3/span[text()='Hoodies']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", hoodiesElement);

        WebElement handlaHarButton = driver.findElement(By.cssSelector("a.fl-button"));
        handlaHarButton.click();

        WebElement productLink = driver.findElement(By.xpath("//a[@href='https://www.ithshop.se/produkt/iths-tygkasse-lila/']//h2[contains(text(),'ITHS tygkasse lila')]"));
        Assertions.assertTrue(productLink.isDisplayed(), "The product link for 'ITHS tygkasse lila' is  present on the page");
        System.out.println("All assertions passed successfully!");
    }
    @Test
    public void checkVikitigaDatum(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement härDuAnsökarLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-hurduansker")));
        //Perform Hover action on the parent menu and make sure all the elements are visible
        Actions actions = new Actions(driver);
        actions.moveToElement(härDuAnsökarLink).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-hurduansker ul.sub-menu")));
        List<WebElement> submenuItems = driver.findElements(By.cssSelector("#nav-hurduansker ul.sub-menu li"));
        WebElement viktigaDatumLink = driver.findElement(By.id("nav-viktigadatum"));
        viktigaDatumLink.click();
        WebElement heroHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.page-item__hero-content h1")));
        String expectedHeaderText = "Viktiga datum";
        String actualHeaderText = heroHeader.getText().trim();

        // Assert the text in the <h1> element
        Assertions.assertEquals(expectedHeaderText, actualHeaderText, "The header text on the 'Viktiga datum' page is a  match!");

        System.out.println("Assertion passed: Header text matches!");
    }
    @Test
    public void checkUtbildningGöteborg() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the "Utbildningar" link in the navigation to be clickable
        WebElement utbildningarLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-utbildningar")));

        // Perform hover action on "Utbildningar" menu and wait for submenu items to be visible
        Actions actions = new Actions(driver);
        actions.moveToElement(utbildningarLink).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-utbildningar ul.sub-menu")));

        // Locate and click the "Göteborg" link from the submenu
        WebElement göteborgLink = driver.findElement(By.xpath("//*[@id=\"nav-gteborg\"]"));
        göteborgLink.click();

        // Wait for the header of the Göteborg page to be visible and retrieve the text
        WebElement pageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main\"]/div[2]/h1")));
        String expectedHeaderText = "IT-utbildningar Göteborg";
        String actualHeaderText = pageHeader.getText().trim();

        // Assert that the header text matches the expected text
        Assertions.assertEquals(expectedHeaderText, actualHeaderText, "The header text on the 'IT-utbildningar Göteborg' page matches!");

        System.out.println("Assertion passed: Header text matches!");
    }
    @Test
    public void checkUtbildningStockholm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the "Utbildningar" link in the navigation to be clickable
        WebElement utbildningarLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-utbildningar")));

        // Perform hover action on "Utbildningar" menu and wait for submenu items to be visible
        Actions actions = new Actions(driver);
        actions.moveToElement(utbildningarLink).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-utbildningar ul.sub-menu")));

        // Locate and click the "Göteborg" link from the submenu
        WebElement stockholmLink = driver.findElement(By.cssSelector("#nav-stockholm"));
        stockholmLink.click();

        // Wait for the header of the Göteborg page to be visible and retrieve the text
        WebElement pageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main\"]/div[2]/h1")));
        String expectedHeaderText = "IT-utbildningar Stockholm";
        String actualHeaderText = pageHeader.getText().trim();

        // Assert that the header text matches the expected text
        Assertions.assertEquals(expectedHeaderText, actualHeaderText, "The header text on the 'IT-utbildningar Stockholm' page matches!");

        System.out.println("Assertion passed: Header text matches and IT-utbildningar Stockholm is seen !");
    }






    @AfterAll
    static void teardown() {
        driver.quit();
    }
}
