/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.logic.frommqtt;

import java.util.ArrayList;

/**
 *
 * @author ale
 */
public class Room {
    
    private String roomtype; 
    private String name; 
    private float x;
    private float y; 
    private float squarex; 
    private float squarey,squarewidth, squareheight; 
    private float xpuppet, ypuppet; 
    private ArrayList<Location> locationList; 
    
    public Room() {
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSquarex() {
        return squarex;
    }

    public void setSquarex(float squarex) {
        this.squarex = squarex;
    }

    public float getSquarey() {
        return squarey;
    }

    public void setSquarey(float squarey) {
        this.squarey = squarey;
    }

    public float getSquarewidth() {
        return squarewidth;
    }

    public void setSquarewidth(float squarewidth) {
        this.squarewidth = squarewidth;
    }

    public float getSquareheight() {
        return squareheight;
    }

    public void setSquareheight(float squareheight) {
        this.squareheight = squareheight;
    }



    public float getXpuppet() {
        return xpuppet;
    }

    public void setXpuppet(float xpuppet) {
        this.xpuppet = xpuppet;
    }

    public float getYpuppet() {
        return ypuppet;
    }

    public void setYpuppet(float ypuppet) {
        this.ypuppet = ypuppet;
    }

    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<Location> locationList) {
        this.locationList = locationList;
    }

    @Override
    public String toString() {
        return "Room{" + "roomtype=" + roomtype + ", name=" + name + ", x=" + x + ", y=" + y + ", xpuppet=" + xpuppet + ", ypuppet=" + ypuppet + ", locationList=" + locationList + '}';
    }
    
    
    
}
