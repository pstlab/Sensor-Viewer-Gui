
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.logic.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */

public class EHouse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String zid; // 25487520
    private List<ERoom> rooms = new ArrayList<>();
    private ESensor entranceDoor;
    private List<ESensor> sensors = new ArrayList<>();
    private List<EUser> users = new ArrayList<>();

    public EHouse() {
    }
    
    public void addERoom(ERoom room){
        this.rooms.add(room);
    }
    
    public void addSensor(ESensor eSensor){
        this.sensors.add(eSensor);
    }
    
    public void addEUser(EUser eUser){
        this.users.add(eUser);
    }

    public List<ESensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<ESensor> sensors) {
        this.sensors = sensors;
    }

    public void setEntranceDoor(ESensor entranceDoor) {
        this.entranceDoor = entranceDoor;
    }
    

    
    public List<ERoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<ERoom> rooms) {
        this.rooms = rooms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean hasEntranceDoor() {
        return entranceDoor != null;
    }

    public ESensor getEntranceDoor() {
        return entranceDoor;
    }

    public List<EUser> getUsers() {
        return users;
    }

    public void setUsers(List<EUser> users) {
        this.users = users;
    }
    
    

    public ERoom whereIsThisSensor(ESensor sensor) {
        for (ERoom room : rooms) {
            List<ESensor> ssss = room.getSensors();
            for (ESensor s : ssss) {
                if (s.getId() == sensor.getId()) {
                    return room;
                }
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
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
        if (!(object instanceof EHouse)) {
            return false;
        }
        EHouse other = (EHouse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "House[ id=" + id + ", name=" + name + " ]";
    }

}
