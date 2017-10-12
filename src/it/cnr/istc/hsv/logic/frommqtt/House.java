/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.logic.frommqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ale
 */
public class House {
    private Long id; 
    private String home_id; 
    private String home_name; 
    private int entrance_id; 
    private ArrayList<Resident> residentList; 
    private ArrayList<Room> roomList; 
    private Map<String, List<String>> topology; 
    
    public House(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public int getEntrance_id() {
        return entrance_id;
    }

    public void setEntrance_id(int entrance_id) {
        this.entrance_id = entrance_id;
    }

    public ArrayList<Resident> getResidentList() {
        return residentList;
    }

    public void setResidentList(ArrayList<Resident> residentList) {
        this.residentList = residentList;
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }

    public Map<String, List<String>> getTopology() {
        return topology;
    }

    public void setTopology(Map<String, List<String>> topology) {
        this.topology = topology;
    }

    @Override
    public String toString() {
        return "House{" + "id=" + id + ", home_id=" + home_id + ", home_name=" + home_name + ", entrance_id=" + entrance_id + ", residentList=" + residentList + ", roomList=" + roomList + ", topology=" + topology + '}';
    }

    
    
}
