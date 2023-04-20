package com.tarladala.recipe.scraping.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {
    public Properties readFile() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\ayesh\\IdeaProjects\\Data_Scraping_Framework_Demo1\\src\\test\\resources");
        Properties properties = new Properties();
        properties.load(fr);
        return properties;
    }
}
