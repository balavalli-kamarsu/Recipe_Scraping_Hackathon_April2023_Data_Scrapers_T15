package com.tarladala.recipe.scraping.comorbidityPcos;

import com.tarladala.recipe.scraping.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class ComorbidityPCos extends BaseClass {

    @Test
    public void extractRecipe() throws InterruptedException {
        WebElementObjects eliminateObjects = new WebElementObjects();
        //JavascriptExecutor js = (JavascriptExecutor)driver;
        driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span/a[text()= 'A Checkerboard Of Roses ( Flower Arrangements)']")).click();
        if (eliminateObjects.equals(eliminateObjects.eliminateLemonGrass)) {
            driver.navigate().back();
        } else {
            WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
           System.out.print(recipeTitle.getText());
        }

    }
}
