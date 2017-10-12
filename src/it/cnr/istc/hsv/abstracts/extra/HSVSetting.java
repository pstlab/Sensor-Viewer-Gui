/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

import java.awt.Color;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class HSVSetting {
    
    private static HSVSetting _instance = null;
    private boolean verbose = true;
    
    public static HSVSetting getInstance() {
        if (_instance == null) {
            _instance = new HSVSetting();
            return _instance;
        } else {
            return _instance;
        }
    }
    
    private HSVSetting() {
        super();
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }
    
    
    public Color getInfoBackgroundColor() {
        try {
            String readProperty = this.readProperty(true, DVPISSettingsProperties.PERSONAL_INFO_BACKGROUND);
            if(readProperty == null){
                return new Color(0,0,0,190);
            }
            boolean black = Boolean.parseBoolean(readProperty);
            if(black){
                System.out.println("BLACK !!!");
                return new Color(0,0,0,190);
            }else{
                System.out.println("DVPIS STANDARD");
                return new Color(255,204,0,230);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Color(0,0,0,190);
        }
    }

    private String readProperty(boolean b, String info) {
        return null;
    }
    
    
}
