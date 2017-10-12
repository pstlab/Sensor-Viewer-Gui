/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luca
 */
public class MathUtil {
    
    public static int MAX_I = 3000;
    public static int MAX_SPACE= 2000000;
    private static final String TASKLIST = "tasklist";
    private static final String KILL = "taskkill /F /IM ";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static List<Double> generateDecelerationValues(double decel, double cap, double minimumAmount) {
        List<Double> values = new ArrayList<Double>();
        List<Double> diffValues = new ArrayList<Double>();
        int startSpace = MAX_SPACE;
        int i = 0;
        double maxedTime = 0;
        for (;;) {
            double space = -0.5 * (decel) * i * i + startSpace;
            if (values.isEmpty()) {
                diffValues.add(0d);
                i++;
            } else {
                double diff = values.get(i - 1) - space;
                if (diff < minimumAmount) {
//                    System.out.println("skipping diff: " + diff);
                    i++;
                } else {
                    diffValues.add(diff);
                    maxedTime+=diff;
                    if (maxedTime > cap || i > MAX_I) {
//                        System.out.println("cap reached: "+maxedTime);
                        break;
                    }
                    i++;
                }
            }
            values.add(space);
//            System.out.println("space = " + space);
        }
        
//        System.out.println("VALUES : ");
        double checkSum = 0;
        for (int j = 0; j < diffValues.size(); j++) {
            checkSum+=diffValues.get(j);
//            System.out.println("diff: " + diffValues.get(j));
        }
//        System.out.println("=================================");
//        System.out.println("check sum: "+checkSum);
        return diffValues;
    }
    
    
    
        public static void killProcess(String serviceName) throws Exception {

            System.out.println(">>> attempt to kill: "+serviceName);
        Process exec = Runtime.getRuntime().exec(KILL + serviceName + " /T");
        
            System.out.println(">>> proccess killed.");
            exec.destroy();
            System.out.println("ubber killed");

    }
    
    
        public static boolean isProcessRunging(String serviceName) throws Exception {

         
        Process p = Runtime.getRuntime().exec(TASKLIST);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        String line;
            System.out.println(" >>> targeting process: "+serviceName);
        while ((line = reader.readLine()) != null) {

            System.out.println(line);
            if (line.contains(serviceName)) {
                System.out.println(" >>> process targeted found");
                return true;
            }
        }

        return false;

    }
        
        
        
    public static void main(String [] args){
        try {
//            System.out.println("\u001B[32m============ tester ============");
            
    
            
//            if(MathUtil.isProcessRunging("vsee.exe")){
//                MathUtil.killProcess("vsee.exe");
//            }
//            
            

            
//
//        
//
//        Date today = new Date();
//        GregorianCalendar gc2 = new GregorianCalendar();
//        gc2.setTime(today);
//        int get = gc2.get(Calendar.DAY_OF_WEEK);
//        System.out.println("get = " + get);
//        Date luned = new Date(today.getTime() - 1000 * 60 * 60 * 24 * ((get == 1 ? 8 : get) - 2));
//        System.out.println("lunedì = "+luned);
//        
//        List<Double> generateDecelerationValues = MathUtil.generateDecelerationValues(12.0d, 20000, 100);
//        for (Double double1 : generateDecelerationValues) {
//            try {
//                Thread.sleep(double1.longValue());
//                System.out.println("uhk: "+double1);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(MathUtil.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(MathUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
