package com.tarladala.recipe.scraping.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class BaseClass {
    public static WebDriver driver;


    @Test
    public WebDriver setUpDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ayesh\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.navigate().to("https://www.tarladalal.com/");
        driver.manage().window().maximize();
        return driver;
    }
}