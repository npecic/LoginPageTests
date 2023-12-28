import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class LoginPageTests {
    WebDriver driver;

    @BeforeMethod
    public void setup() {

        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.saucedemo.com/v1/");

    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }

    @Test
    public void standardUserLogin() {

        String item1 = "Sauce Labs Bolt T-Shirt";
        String item2 = "Sauce Labs Bike Light";

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();


        driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../..//div[3]/button")).click();
        driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../..//div[3]/button")).click();


        String item1Price = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../..//div[3]/div")).getText();
        String item2Price = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../..//div[3]/div")).getText();
        String item1Name = driver.findElement(By.xpath("//div[text()='" + item1 + "']")).getText();
        String item2Name = driver.findElement(By.xpath("//div[text()='" + item2 + "']")).getText();
        String item1Description = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../div")).getText();
        String item2Description = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../div")).getText();

        driver.findElement(By.cssSelector(".svg-inline--fa.fa-shopping-cart.fa-w-18.fa-3x")).click();

        String item1CartPrice = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../..//div[@class='inventory_item_price']")).getText();
        String item2CartPrice = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../..//div[@class='inventory_item_price']")).getText();
        String item1CartName = driver.findElement(By.xpath("//div[text()='" + item1 + "']")).getText();
        String item2CartName = driver.findElement(By.xpath("//div[text()='" + item2 + "']")).getText();
        String item1DescriptionCart = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../div")).getText();
        String item2DescriptionCart = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../div")).getText();

        Assert.assertEquals(item1Price, "$" + item1CartPrice);
        Assert.assertEquals(item2Price, "$" + item2CartPrice);
        Assert.assertEquals(item1Name, item1CartName);
        Assert.assertEquals(item2Name, item2CartName);
        Assert.assertEquals(item1Description, item1DescriptionCart);
        Assert.assertEquals(item2Description, item2DescriptionCart);


        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='cart_list']/div[3]//div[@class = 'inventory_item_name']")).getText(), item1);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='cart_list']/div[4]//div[@class = 'inventory_item_name']")).getText(), item2);

        driver.findElement(By.cssSelector(".btn_action.checkout_button")).click();

        driver.findElement(By.id("first-name")).sendKeys("Jessie");
        driver.findElement(By.id("last-name")).sendKeys("James");
        driver.findElement(By.id("postal-code")).sendKeys("36200");

        driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();

        String item1CartPriceCheckOut = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../..//div[@class='inventory_item_price']")).getText();
        String item2CartPriceCheckOut = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../..//div[@class='inventory_item_price']")).getText();
        String item1CartNameCheckOut = driver.findElement(By.xpath("//div[text()='" + item1 + "']")).getText();
        String item2CartNameCheckOut = driver.findElement(By.xpath("//div[text()='" + item2 + "']")).getText();
        String item1DescriptionCheckOut = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../div")).getText();
        String item2DescriptionCheckOut = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../div")).getText();

        Assert.assertEquals(item1Price, item1CartPriceCheckOut);
        Assert.assertEquals(item2Price, item2CartPriceCheckOut);
        Assert.assertEquals(item1Name, item1CartNameCheckOut);
        Assert.assertEquals(item2Name, item2CartNameCheckOut);
        Assert.assertEquals(item1Description, item1DescriptionCheckOut);
        Assert.assertEquals(item2Description, item2DescriptionCheckOut);


        String itemTotalPrice = driver.findElement(By.cssSelector(".summary_subtotal_label")).getText().replace("Item total: $", "");
        double itemTotalPriceCalc = Double.parseDouble(item1Price.replace("$", "")) + Double.parseDouble(item2Price.replace("$", ""));

        Assert.assertEquals(Double.parseDouble(itemTotalPrice), itemTotalPriceCalc);

        driver.findElement(By.cssSelector(".btn_action.cart_button")).click();


        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/v1/checkout-complete.html");


    }

    @Test
    public void lockedOutUserLogin() {

        String item1 = "Sauce Labs Bolt T-Shirt";
        String item2 = "Sauce Labs Bike Light";

        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();

        String errorM = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3")).getText();

        Assert.assertEquals(errorM, "Epic sadface: Sorry, this user has been locked out.");

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/v1/");


    }

    @Test
    public void misspelledUserLogin() {
        String item1 = "Sauce Labs Bolt T-Shirt";
        String item2 = "Sauce Labs Bike Light";

        driver.findElement(By.id("user-name")).sendKeys("standard1_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();


        String errorM = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3")).getText();

        Assert.assertEquals(errorM, "Epic sadface: Username and password do not match any user in this service");

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/v1/");


    }


    @Test
    public void performanceUserLogin() {
        String item1 = "Sauce Labs Bolt T-Shirt";
        String item2 = "Sauce Labs Bike Light";

        driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/v1/inventory.html");


        driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../..//div[3]/button")).click();
        driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../..//div[3]/button")).click();


        String item1Price = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../..//div[3]/div")).getText();
        String item2Price = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../..//div[3]/div")).getText();
        String item1Name = driver.findElement(By.xpath("//div[text()='" + item1 + "']")).getText();
        String item2Name = driver.findElement(By.xpath("//div[text()='" + item2 + "']")).getText();
        String item1Description = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../div")).getText();
        String item2Description = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../div")).getText();

        driver.findElement(By.cssSelector(".svg-inline--fa.fa-shopping-cart.fa-w-18.fa-3x")).click();

        String item1CartPrice = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../..//div[@class='inventory_item_price']")).getText();
        String item2CartPrice = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../..//div[@class='inventory_item_price']")).getText();
        String item1CartName = driver.findElement(By.xpath("//div[text()='" + item1 + "']")).getText();
        String item2CartName = driver.findElement(By.xpath("//div[text()='" + item2 + "']")).getText();
        String item1DescriptionCart = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../div")).getText();
        String item2DescriptionCart = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../div")).getText();

        Assert.assertEquals(item1Price, "$" + item1CartPrice);
        Assert.assertEquals(item2Price, "$" + item2CartPrice);
        Assert.assertEquals(item1Name, item1CartName);
        Assert.assertEquals(item2Name, item2CartName);
        Assert.assertEquals(item1Description, item1DescriptionCart);
        Assert.assertEquals(item2Description, item2DescriptionCart);


        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='cart_list']/div[3]//div[@class = 'inventory_item_name']")).getText(), item1);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='cart_list']/div[4]//div[@class = 'inventory_item_name']")).getText(), item2);

        driver.findElement(By.cssSelector(".btn_action.checkout_button")).click();

        driver.findElement(By.id("first-name")).sendKeys("Jessie");
        driver.findElement(By.id("last-name")).sendKeys("James");
        driver.findElement(By.id("postal-code")).sendKeys("36200");

        driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();

        String item1CartPriceCheckOut = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../..//div[@class='inventory_item_price']")).getText();
        String item2CartPriceCheckOut = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../..//div[@class='inventory_item_price']")).getText();
        String item1CartNameCheckOut = driver.findElement(By.xpath("//div[text()='" + item1 + "']")).getText();
        String item2CartNameCheckOut = driver.findElement(By.xpath("//div[text()='" + item2 + "']")).getText();
        String item1DescriptionCheckOut = driver.findElement(By.xpath("//div[text()='" + item1 + "']/../../div")).getText();
        String item2DescriptionCheckOut = driver.findElement(By.xpath("//div[text()='" + item2 + "']/../../div")).getText();

        Assert.assertEquals(item1Price, item1CartPriceCheckOut);
        Assert.assertEquals(item2Price, item2CartPriceCheckOut);
        Assert.assertEquals(item1Name, item1CartNameCheckOut);
        Assert.assertEquals(item2Name, item2CartNameCheckOut);
        Assert.assertEquals(item1Description, item1DescriptionCheckOut);
        Assert.assertEquals(item2Description, item2DescriptionCheckOut);


        String itemTotalPrice = driver.findElement(By.cssSelector(".summary_subtotal_label")).getText().replace("Item total: $", "");
        double itemTotalPriceCalc = Double.parseDouble(item1Price.replace("$", "")) + Double.parseDouble(item2Price.replace("$", ""));

        Assert.assertEquals(Double.parseDouble(itemTotalPrice), itemTotalPriceCalc);

        driver.findElement(By.cssSelector(".btn_action.cart_button")).click();


        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/v1/checkout-complete.html");

    }

}





