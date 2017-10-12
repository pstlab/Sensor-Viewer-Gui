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

public class ERoom implements Serializable {

    private Long id;
    private ERoomType roomType;
    private List<ESensor> sensors = new ArrayList<>();
    private String name;
    private float x;
    private float y;
    private float squareX;
    private float squareY;
    private float squareWidth;
    private float squareHeight;

    private float xPuppet;
    private float yPuppet;
    
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

    public float getxPuppet() {
        return xPuppet;
    }

    public void setxPuppet(float xPuppet) {
        this.xPuppet = xPuppet;
    }

    public float getyPuppet() {
        return yPuppet;
    }

    public void setyPuppet(float yPuppet) {
        this.yPuppet = yPuppet;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(ERoomType roomType) {
        this.roomType = roomType;
    }

    public List<ESensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<ESensor> sensors) {
        this.sensors = sensors;
    }

    public float getSquareX() {
        return squareX;
    }

    public void setSquareX(float squareX) {
        this.squareX = squareX;
    }

    public float getSquareY() {
        return squareY;
    }

    public void setSquareY(float squareY) {
        this.squareY = squareY;
    }

    public float getSquareWidth() {
        return squareWidth;
    }

    public void setSquareWidth(float squareWidth) {
        this.squareWidth = squareWidth;
    }

    public float getSquareHeight() {
        return squareHeight;
    }

    public void setSquareHeight(float squareHeight) {
        this.squareHeight = squareHeight;
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
        if (!(object instanceof ERoom)) {
            return false;
        }
        ERoom other = (ERoom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.istc.senseserver.db.entity.Room[ id=" + id + " ]";
    }
    
}
