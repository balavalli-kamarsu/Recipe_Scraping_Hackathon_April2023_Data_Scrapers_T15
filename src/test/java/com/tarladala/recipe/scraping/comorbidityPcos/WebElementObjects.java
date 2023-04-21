package com.tarladala.recipe.scraping.comorbidityPcos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebElementObjects {
    //WebDriver driver;

    /*public WebElementObjects() {
        this.driver = d;
    }*/

    By eliminateCake = By.xpath("//span[contains (text(), 'cake')]");
    By eliminateLemonGrass = By.xpath("//*[contains (text(), 'lemongrass')]");

  /*  public void findCakeEliminate() {
        driver.findElement(eliminateCake);
    }*/
}