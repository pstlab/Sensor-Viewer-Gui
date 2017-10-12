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

public class EUser implements Serializable {

    private Long id;
    private String name;
    private String surname;
    
//    private float xPuppet;
//    private float yPuppet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

//    public float getxPuppet() {
//        return xPuppet;
//    }
//
//    public void setxPuppet(float xPuppet) {
//        this.xPuppet = xPuppet;
//    }
//
//    public float getyPuppet() {
//        return yPuppet;
//    }
//
//    public void setyPuppet(float yPuppet) {
//        this.yPuppet = yPuppet;
//    }
    
    
      

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof EUser)) {
            return false;
        }
        EUser other = (EUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name +" "+this.surname;
    }
    
}
