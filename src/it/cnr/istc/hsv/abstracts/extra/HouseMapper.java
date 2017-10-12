/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ESensor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class HouseMapper {

    public static List<ESensor> getPIRSensorListByHouse(EHouse house) {
        List<ESensor> pirs = new ArrayList<>();
        List<ESensor> sensors = house.getSensors();
        for (ESensor sensor : sensors) {
            if(sensor.getSensorType().getName().toLowerCase().contains("pir")){
                pirs.add(sensor);
            }
        }
        return pirs;
        
    }
    
}
