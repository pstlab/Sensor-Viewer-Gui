/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it>
 */
public class Configuration {

    private static Configuration _instance = null;
    private boolean releaseActualTurns = false;
    private static final String SERVER_IP = "serverip";
    private static final String TEST = "test";
    public String ip = "localhost";
    private boolean test = false;

    public static Configuration getInstance() {
        if (_instance == null) {
            _instance = new Configuration();
            return _instance;
        } else {
            return _instance;
        }
    }

    private Configuration() {
        super();
        loadConfiguration();
    }

    public boolean isReleaseActualTurns() {
        return releaseActualTurns;
    }

    public String getIp() {
        return ip;
    }

    public boolean isTest() {
        return test;
    }
    
    

    private void loadConfiguration() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            ip = prop.getProperty(SERVER_IP);
            test = Boolean.valueOf(prop.getProperty(TEST));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
