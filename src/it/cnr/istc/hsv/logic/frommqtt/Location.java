/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.logic.frommqtt;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ale
 */
public class Location {
    
    private String type; 
    private int xmap; 
    private int ymap; 
    private ArrayList<Sensor> sensorList; 

    public Location() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getXmap() {
        return xmap;
    }

    public void setXmap(int xmap) {
        this.xmap = xmap;
    }

    public int getYmap() {
        return ymap;
    }

    public void setYmap(int ymap) {
        this.ymap = ymap;
    }

    public ArrayList<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(ArrayList<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    @Override
    public String toString() {
        return "Location{" + "type=" + type + ", xmap=" + xmap + ", ymap=" + ymap + ", sensorList=" + sensorList + '}';
    }
    
    
    
}
