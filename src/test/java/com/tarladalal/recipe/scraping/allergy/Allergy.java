package com.tarladalal.recipe.scraping.allergy;
import com.tarladalal.recipe.scraping.base.BaseClass;
import com.tarladalal.recipe.scraping.utilities.WriteExcel;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Allergy extends BaseClass {


	@Test
	public void allrecipe () throws InterruptedException, IOException
	{
		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
		Thread.sleep(2000);
		int setCellvalue=1;
		
		// run in a loop for all recipe in a page
		List< String> pageBeginsWithList = Arrays.asList(new String[]{"0-9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"});
		for(int k=0; k < pageBeginsWithList.size(); k++) {
			driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+pageBeginsWithList.get(k));
			int lastPage =0;
			try {
				lastPage= Integer.parseInt(driver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
			} catch ( Exception e) {
				
				//do nothing or log exception
			}
			if (0 != lastPage) {
				for (int j = 1; j <= lastPage; j++) {
					int pageindex = j;

					driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+pageBeginsWithList.get(k)+"&pageindex=" + j);
					List<WebElement> recipeCardElements = driver.findElements(By.xpath("//div[@class='rcc_recipecard']"));
					List<String> recipeUrls = new ArrayList<>();
					Map<String, String> recipeIdUrls = new HashMap<>();

					//Looping through all recipes Web elements and generating a navigation URL
					recipeCardElements.stream().forEach(recipeCardElement -> {
						recipeUrls.add("https://www.tarladalal.com/" + recipeCardElement.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));
						//example: recipeIdUrls.put("id","url");=> Extracted Recipe Id and Recipe URL Here and added to a hashmap
						recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp",""),"https://www.tarladalal.com/" + recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href"));
					});

					for (Map.Entry<String,String> recipeIdUrlEntry : recipeIdUrls.entrySet())  {
						String recipeUrl = recipeIdUrlEntry.getValue();
						String recipeId = recipeIdUrlEntry.getKey();
						driver.navigate().to(recipeUrl);
						driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

						List<String> eliminators = Arrays.asList(new String[]{"milk","soy","egg","Sesame","peanuts","walnut","almond","hazelnut","pecan","cashew","pistachio","shell fish","seafood"});
						for(int i = 0; i < eliminators.size();i++) {
							String sheetname=eliminators.get(i)+"Allergies";
							
							if (isEliminated(eliminators.get(i))) {
							}

							else {

								System.out.println("Print Setvalue inside elese:"+setCellvalue);
								WriteExcel writeOutput = new WriteExcel();
								WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
								System.out.println(recipeTitle.getText());
								writeOutput.setCellAllergyData(sheetname, setCellvalue,1, recipeTitle.getText());
								Thread.sleep(2000);

								//preparation time
								try {
									WebElement preprationtime = driver.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
									((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", preprationtime);
									System.out.println("Preparation Time:"+" "+preprationtime.getText());
									writeOutput.setCellAllergyData(sheetname, setCellvalue,5, preprationtime.getText());
								} catch (Exception e) {
									// foodCategory
								}

								// recipeCategory
								try {
									WebElement recipeCategory = driver.findElement(By.xpath("//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));
									writeOutput.setCellAllergyData(sheetname, setCellvalue, 2, recipeCategory.getText());
									System.out.print(recipeCategory.getText());
								} catch (Exception e) {
									// foodCategory
								}
								try {
									WebElement foodCategory = driver.findElement(By.xpath("//a/span[text() = 'No Cooking Veg Indian']"));
									writeOutput.setCellAllergyData(sheetname, setCellvalue, 3, foodCategory.getText());
									System.out.print(foodCategory.getText());
								} catch (Exception e) {}

								//cooking time
								try {
									WebElement cookingtime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
									System.out.println("Cooking Time:"+" "+cookingtime.getText());
									writeOutput.setCellAllergyData(sheetname, setCellvalue,6, cookingtime.getText());
								} catch (Exception e) {
									// foodCategory
								}

								try {
									//Preparation Method
									WebElement preprationMethod = driver.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
									System.out.println("Preparation Method:"+" "+preprationMethod.getText());
									writeOutput.setCellAllergyData(sheetname, setCellvalue,7, preprationMethod.getText());
								} catch (Exception e) {
									// foodCategory
								}

								// print`Ingredients list
								try {
									WebElement extractIngrendientlist = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
									System.out.println("Ingredients List:"+" "+extractIngrendientlist.getText());
									writeOutput.setCellAllergyData(sheetname, setCellvalue,4, extractIngrendientlist.getText());
								} catch (Exception e) {
									// foodCategory
								}
								//Nutrients value of recipe


								try {
									WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
									writeOutput.setCellAllergyData(sheetname, setCellvalue, 8, nutrients.getText());
									System.out.print(nutrients.getText());
								} catch (Exception e) {

								}

								//Recipe Current URL
								try {
									String strUrl = driver.getCurrentUrl();
									System.out.println("Recipe Url is:"+ strUrl);
									writeOutput.setCellAllergyData(sheetname, setCellvalue,9, strUrl);

									//Recipe ID from Current URL

									String regex = "\\d+";
									Pattern pattern = Pattern.compile(regex);
									Matcher matcher = pattern.matcher(strUrl);
									if (matcher.find()) {
										int Receipeid = Integer.parseInt(matcher.group());
										String Receipeidstr = Integer.toString(Receipeid);
										writeOutput.setCellAllergyData(sheetname, setCellvalue,0, Receipeidstr);
									}
								} catch (Exception e) {
									// foodCategory
								}
								Thread.sleep(1000);
							}
						}						
						setCellvalue++;
					}
				}
			}
		}
	}

	private boolean isEliminated(String string) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

		try {
			WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
			String ingredients = ingredientWebElement.getText();
			if (null != ingredients && null != string && ingredients.toLowerCase().contains(string.toLowerCase())) {
				isEliminatorPresent.set(true);
			}
		} catch (Exception e) {
			System.out.print("No Such Element " + e.getLocalizedMessage());
			System.out.print("Helooooooooooo -  Fail");
		}
		try {

			WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
			String method = methodWebElement.getText();
			if (null != method && null != string && method.toLowerCase().contains(string.toLowerCase())) {
				isEliminatorPresent.set(true);
			}
		} catch (Exception e) {
			System.out.print("No Such Element " + e.getLocalizedMessage());
			System.out.print("Helooooooooooo -  Fail");
		}
		return isEliminatorPresent.get();
	}
}