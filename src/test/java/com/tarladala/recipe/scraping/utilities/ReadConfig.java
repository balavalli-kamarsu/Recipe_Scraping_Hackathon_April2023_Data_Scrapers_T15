package com.tarladala.recipe.scraping.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {
    public Properties readFile() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\valli\\Git\\Recipe_Scraping_Hackathon_April2023_Data_Scrapers_T15\\src\\test\\resources");
        Properties properties = new Properties();
        properties.load(fr);
        return properties;
    }
}
