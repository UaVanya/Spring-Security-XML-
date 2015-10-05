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
            File file = new File(logPropertiesConsole);
            String ss = file.getAbsolutePath();
            try {
                logProperty.load(new FileInputStream(logPropertiesConsole));
                PropertyConfigurator.configure(logProperty);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (false) {
            try {
                logProperty.load(new FileInputStream(logPropertiesFile));
                PropertyConfigurator.configure(logProperty);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String path = getPathFileLog();
        PropertyConfigurator.configure(path);
    }

    public String getPathFileLog() {
        String env = System.getenv("CATALINA_LOG4J");
        if (env.equals("true")) {
            return new File(logPropertiesConsole).getAbsoluteFile().toString();
        } else {
            return new File(logPropertiesFile).getAbsoluteFile().toString();
        }
    }

}
