package com.tarladala.recipe.scraping.comorbidityHypertension;

import com.tarladala.recipe.scraping.base.BaseClass;
import com.tarladala.recipe.scraping.utilities.WriteExcel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComorbidityHypertension extends BaseClass {


    @Test
    public void extractRecipe() throws InterruptedException, IOException {

        //JavascriptExecutor js = (JavascriptExecutor)driver;
        driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
        Thread.sleep(2000);
        int rowCounter =1;
        // run in a loop for all recipe in a page

        List<WebElement> recipeElements = driver.findElements(By.className("rcc_recipename"));
        List<String> recipeUrls = new ArrayList<>();
        //Looping through all recipes Web elements and generating a navigation URL
        recipeElements.stream().forEach(recipeElement -> {
            recipeUrls.add("https://www.tarladalal.com/" + recipeElement.findElement(By.tagName("a")).getDomAttribute("href"));
        });

        for(int i=0; i<recipeUrls.size(); i++) {
            driver.navigate().to(recipeUrls.get(i));
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            WebElement ingredientList = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));

            List<String> eliminators = Arrays.asList(new String[]{
            		 "caffeine", "coffee","tea","alcohol","bacon","ham","frozen food","pickles","proccessed",
	        		"canned","fried","sauces","mayonnaise","sausages","deli","meat","white rice","white bread","chips",
	        		"pretzels","crackers"
                    });


            if (isEliminated(ingredientList, eliminators)) {
                //driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
            } else {
               // WebElement recipeTitle = getElementText();
               // WebElement recipeCategory = null;
                WriteExcel writeOutput = new WriteExcel();
               
                //Recipe id
                try {
//                	String recipeId = (recipeUrls.get(i).substring(recipeUrls.lastIndexOf('-')+1))
 //               	.substring(1,recipeUrls.get(i).substring(recipeUrls.lastIndexOf('-')+1).length()-1);
                	
                     System.out.println(recipeUrls.get(i));
                     writeOutput.setCellData("Hypertension", rowCounter, 0, 
                    recipeUrls.get(i).substring(recipeUrls.lastIndexOf('-')+1).substring(1,recipeUrls.get(i).substring(recipeUrls.lastIndexOf('-')+1).length()-1));

                 } catch (Exception e) {

                 }

                //Recipe Name
                try {
                   WebElement recipeTitle = driver.findElement(By.id("ctl00_cntrightpanel_lblRecipeName"));
                    System.out.print(recipeTitle.getText());
                    writeOutput.setCellData("Hypertension", rowCounter, 1, recipeTitle.getText());

                } catch (Exception e) {

                }
                
           //Recipe Category
                try {
                   WebElement recipeCategory = driver.findElement(By.xpath("//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));
                    System.out.print(recipeCategory.getText());
                   writeOutput.setCellData("Hypertension", i+1, 2, recipeCategory.getText());

                } catch (Exception e) {

                }
                
           // Food Category
                try {
                    WebElement foodCategory = driver.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));
                    System.out.print(foodCategory.getText());
                    writeOutput.setCellData("Hypertension", i+1, 3, foodCategory.getText());

                } catch (Exception e) {

                }

           // Ingredients List
                try {
                    WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
                    System.out.print(nameOfIngredients.getText());
                    writeOutput.setCellData("Hypertension", i+1, 4, nameOfIngredients.getText());

                } catch (Exception e) {

                }
                
           // Preparation Time
                try {
                    WebElement preparationTime = driver.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
                    System.out.print(preparationTime.getText());
                    writeOutput.setCellData("Hypertension", i+1, 5, preparationTime.getText());

                } catch (Exception e) {

                }

           // Cook Time
                try {
                    WebElement cookTime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
                    System.out.print(cookTime.getText());
                    writeOutput.setCellData("Hypertension", i+1, 6, cookTime.getText());

                } catch (Exception e) {

                }

          // Preparation Method
                try {
                    WebElement prepMethod = driver.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
                    System.out.print(prepMethod.getText());
                    writeOutput.setCellData("Hypertension", i+1, 7, prepMethod.getText());

                } catch (Exception e) {

                }
                
          // Nutrients List
                try {
                    WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
                    System.out.print(nutrients.getText());
                    writeOutput.setCellData("Hypertension", i+1, 8, nutrients.getText());

                } catch (Exception e) {

                }

          // Recipe Url
                try {
                    String recipeUrl = recipeUrls.get(i);
                    System.out.print(recipeUrl);
                    writeOutput.setCellData("Hypertension", i+1, 9, recipeUrl);

                } catch (Exception e) {

                }

            }

            rowCounter++;
        }
    }

    /*private static WebElement getElementText() {
        return driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
    }*/

    private boolean isEliminated(WebElement rcpinglist, List<String> eliminators) {
        AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);
        eliminators.parallelStream().forEach(eliminator -> {
            try {
                if (null != rcpinglist.findElement(By.xpath("//*[text() ='" + eliminator + "']"))) {
                    isEliminatorPresent.set(true);
                }
            } catch (Exception e) {
                System.out.print("No Such Element " + e.getLocalizedMessage());
            }
        });
        return isEliminatorPresent.get();
    }
}