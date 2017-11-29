/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.logic.frommqtt;

/**
 *
 * @author ale
 */
public class Sensor {
    
    private String id; 
    private String sid; 
    private String name;   
    private String node_id; 
    private String state; 
    private String sensortype; 
    private String label;
    private String unit;
    private String type;
//    private String value; 
    private SensorData lastValue;

    public Sensor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSensortype() {
        return sensortype;
    }

    public void setSensortype(String sensortype) {
        this.sensortype = sensortype;
    }

    public SensorData getLastValue() {
        return lastValue;
    }

    public void setLastValue(SensorData lastValue) {
        this.lastValue = lastValue;
    }

 
    

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    
    @Override
    public String toString() {
        return "Sensor{" + "id=" + id + ", sid=" + sid + ", name=" + name + ", node_id=" + node_id + ", state=" + state + ", sensortype=" + sensortype + '}';
    }


    
    
}
