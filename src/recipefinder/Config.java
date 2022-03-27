/*
 * Creates the Configuration file for the Application.
 */
//This is a test edit for the application things
//Another one :)))
//Ir works
package recipefinder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.awt.Color;

/**
 *
 * @author Adrin Alias
 */
public class Config {
    public static Properties prop = new Properties();
    
    /**
     * @param key
     * @param value
     */
    public static void setProperty(String key, String value) {
        //Sets a property in the Config file.
        try{
            prop.setProperty(key, value);
            prop.store(new FileOutputStream("Config.txt"), null);
        }catch(IOException e) {
        }
    }    

    /**
     * @param key
     * @return 
     */
    public static String getProperty(String key) {
        //Gets the value of the passed property.
        String value = "";
        try{
            prop.load(new FileInputStream("Config.txt"));
            value=prop.getProperty(key);
            
        }catch(IOException e){}
        
        return value; // Returns the value of the property.
    }
    
    /**
     *
     * @return
     */
    public static Color getColor() {
        //Returns the Color from the config file.
        Color col = new Color(0);
        try{
            prop.load(new FileInputStream("Config.txt"));
            col = new Color(Integer.parseInt(prop.getProperty("Color")));
            
        }catch(IOException e){}
        
        return col; // Returns the value of the property.    
    }
    
}

