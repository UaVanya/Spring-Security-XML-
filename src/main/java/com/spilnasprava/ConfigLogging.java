package com.spilnasprava;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLogging {
    private static Properties logProperty = new Properties();
    private final String logPropertiesConsole = "src/main/resources/log4j-console.properties";
    private final String logPropertiesFile = "/log4j-catalina.properties";

    public void init() {
        String catalinaLog4J = System.getenv("CATALINA_LOG4J");
        if (catalinaLog4J != null) ;
        if (catalinaLog4J.equals("true")) {
            setLogProperty(logPropertiesConsole);
        } else if (catalinaLog4J.equals("false")) {
            setLogProperty(logPropertiesFile);
        }
        PropertyConfigurator.configure(getPathFileLog());
    }

    public String getPathFileLog() {
        String env = System.getenv("CATALINA_LOG4J");
        if (env.equals("true")) {
            return new File(logPropertiesConsole).getAbsoluteFile().toString();
        } else if (env.equals("false")) {
            return new File(logPropertiesFile).getAbsoluteFile().toString();
        }
        return null;
    }

    public void setLogProperty(String pathLogProperties) {
        try (FileInputStream fileInputStream = new FileInputStream(pathLogProperties);) {
            logProperty.load(fileInputStream);
            PropertyConfigurator.configure(logProperty);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
