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
public class Resident {
    
    private String name; 
    private String surname; 

    public Resident() {
    }

    
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

    @Override
    public String toString() {
        return "Resident{" + "name=" + name + ", surname=" + surname + '}';
    }
    
    
    
}
