/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

/**
 *
 * @author Luca
 */
public class DVPISSettingsProperties {
    
    public static String PERSONAL_CERTIFICATE_PATH = "certificate.path";
    public static String DEFAULT_NULL_CERTIFICATE = "none";
    public static String PERSONAL_CURRENT_LANGUAGE = "lang";
    public static String TRUST_PATH = "trust.path";
    public static String TRUST_PASS = "trust.pass";
    public static String VERSION = "version";
    public static String USER_MANUAL = "user.manual.file";
    public static String PERSONAL_VERBOSE = "verbose";
    public static String PERSONAL_PASSSAVED = "pass.saved";
    public static String PERSONAL_SHOW_ARROW = "show.arrows";
    public static String PERSONAL_SHOW_ARROW_LIMIT = "show.arrows.limit";
    public static String PERSONAL_INFO_BACKGROUND = "info.background";
    
    //GUI SETTINGS
    public static String DISPLAY_OVERVIEW = "display.overview";
    
    //PEOPLE ENBALED
    public static String PEOPLE_ENABLED = "people.enabled";
    
    public static String STANDARD_CERTIFICATE_FILE_NAME = "client.jks";
    public static String STANDARD_CERTIFICATE_FOLDER = "authentication";
    
    public static String RUNNING_MODE = "running.mode";
    public static String GIRAFF_PILOT_LOADING = "giraff.pilot.load";
    public static String PRIMARY_GUI_LOADING= "primary.gui";
    public static String MAIN_FRAME_LAYOUT = "layout";
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    
    public enum MAIN_LAYOUT{
    
        NEW("new"),
        OLD("old");
        
        private String label;

        public String getLabel() {
            return label;
        }

        private MAIN_LAYOUT(String label) {
            this.label = label;
        } 
        
        public static MAIN_LAYOUT fromString(String lang){
            if(lang.equals("new")){
                return MAIN_LAYOUT.NEW;
            }else if(lang.equals("old")){
                return MAIN_LAYOUT.OLD;
            }else{
                return MAIN_LAYOUT.OLD;
            }
        }
        
        
    }
    
    public enum LANGUAGE {
    
        EN("English"),
        IT("Italian"),
        SE("Swedish"),
        ES("Spanish");
        
        private String label;

        public String getLabel() {
            return label;
        }

        private LANGUAGE(String label) {
            this.label = label;
        } 
        
        public static LANGUAGE fromString(String lang){
            if(lang.equals("English")){
                return LANGUAGE.EN;
            }else if(lang.equals("Italian")){
                return LANGUAGE.IT;
            }else if(lang.equals("Swedish")){
                return LANGUAGE.SE;
            }else if(lang.equals("Spanish")){
                return LANGUAGE.ES;
            }else{
                //default
                return LANGUAGE.EN;
            }
        }
        
        
    }
    
}
