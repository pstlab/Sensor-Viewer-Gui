/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.tests;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class Persona {
    private String name;
    private String surname;
    
    private List<Cane> cani = new ArrayList<>();

    public Persona() {
    }

    public Persona(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

    public List<Cane> getCani() {
        return cani;
    }

    public void setCani(List<Cane> cani) {
        this.cani = cani;
    }
    
    public void addCane(Cane cane){
        this.cani.add(cane);
    }
    
    
}
