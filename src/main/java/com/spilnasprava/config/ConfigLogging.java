package com.spilnasprava.config;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLogging {
    private static Properties logProperty = new Properties();
    private final String logPropertiesFile = "logs/log4j-catalina.properties";
    private final String logPropertiesConsole = "logs/log4j-console.properties";

    public ConfigLogging() {
        init();
    }

    //    @PostConstruct
    public void init() {
        String catalinaLog4J = System.getenv("CATALINA_LOG4J");
        if (catalinaLog4J != null) {
            if (catalinaLog4J.equals("true")) {
                setLogConfigure(logPropertiesFile);
            } else if (catalinaLog4J.equals("false")) {
                setLogConfigure(logPropertiesConsole);
            }
        } else {
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                System.out.printf("Did not find environment variable!");
            }
        }
    }

    public void setLogConfigure(String pathLogProperties) {
        try (FileInputStream fileInputStream1 = new FileInputStream(pathLogProperties);) {
            logProperty.load(fileInputStream1);
            PropertyConfigurator.configure(logProperty);
            Logger logger = Logger.getLogger(ConfigLogging.class);
            logger.info("Settings log4j are successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}