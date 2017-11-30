/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.logic.entities;

import java.io.Serializable;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */

public class ESensor implements Serializable {

    private Long id;

    private ESensorType sensorType;
    private String name;
    private String location;
    private float xMap;
    private float yMap;
    private boolean multiSensor;
    private ESensorData lastSensorData = null;
    private ESensorData switchData = null;
    private String sid;
    private String nodeId;
    
    
    public boolean isSwitch(){
        return switchData != null;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return nodeId;
    }
    
    
    
    public boolean isSwitchedOn(){
        if(isSwitch() && switchData != null){
            String value = switchData.getValue();
            boolean v = Boolean.parseBoolean(value);
            return v == true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
    
    

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ESensorData getSwitchData() {
        return switchData;
    }

    public void setSwitchData(ESensorData switchData) {
        this.switchData = switchData;
    }

    public ESensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(ESensorType sensorType) {
        this.sensorType = sensorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getxMap() {
        return xMap;
    }

    public void setxMap(float xMap) {
        this.xMap = xMap;
    }

    public float getyMap() {
        return yMap;
    }

    public void setyMap(float yMap) {
        this.yMap = yMap;
    }

    public boolean isMultiSensor() {
        return multiSensor;
    }

    public void setMultiSensor(boolean multiSensor) {
        this.multiSensor = multiSensor;
    }

    public ESensorData getLastSensorData() {
        return lastSensorData;
    }

    public void setLastSensorData(ESensorData lastSensorData) {
        this.lastSensorData = lastSensorData;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ESensor)) {
            return false;
        }
        ESensor other = (ESensor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.istc.senseserver.db.entity.Sensor[ id=" + id + " ]";
    }
    
}
