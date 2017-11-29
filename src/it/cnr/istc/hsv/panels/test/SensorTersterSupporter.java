/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.panels.test;

import it.cnr.istc.hsv.logic.OptionLabelManager;
import it.cnr.istc.hsv.logic.entities.ESensor;
import it.cnr.istc.hsv.logic.entities.ESensorData;
import it.cnr.istc.hsv.mqtt.MQTTManager;
import javax.swing.JPanel;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public abstract class SensorTersterSupporter extends JPanel {

    private ESensor sensor;

    public SensorTersterSupporter(ESensor sensor) {
        this.sensor = sensor;

    }

    public void init() {
        this.setSensorNameLabel("("+sensor.getSid()+") " + sensor.getName() + " " + sensor.getLocation() + " "+sensor.getSensorType().getName());
    }

    public ESensor getSensor() {
        return sensor;
    }

    public void setSensor(ESensor sensor) {
        this.sensor = sensor;
    }

    public void trig(ESensorData data) {
        MQTTManager.getInstance().newSensorData(data);
    }
    
    public void hide(){
        OptionLabelManager.getInstance().hide(this.sensor.getSid());
    }
    
    public void show(){
        OptionLabelManager.getInstance().show(this.sensor.getSid());
    }

    public abstract void setSensorNameLabel(String name);

}
