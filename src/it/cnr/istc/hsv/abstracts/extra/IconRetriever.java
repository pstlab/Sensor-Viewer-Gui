/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;
//
//import static it.cnr.istc.dvpis.common.UserType.GP_ENGINEER;
//import static it.cnr.istc.dvpis.common.UserType.PRIMARY_USER;
//import static it.cnr.istc.dvpis.common.UserType.SECONDARY_USER;
//import it.cnr.istc.dvpis.common.flow.settings.DVPISSettingsProperties;
//import static it.cnr.istc.dvpis.common.flow.settings.DVPISSettingsProperties.LANGUAGE.SE;
//import it.cnr.istc.dvpis.gui.spot.people.logic.ChatPanelManager;
//import it.cnr.istc.dvpis.personalization.PersonalizationManager;
//import it.cnr.istc.dvpis.reports.ReportManager;
//import it.cnr.istc.giraffplus.caching.api.StaticProfile;
//import it.cnr.istc.giraffplus.caching.api.UserCategory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//import si.xlab.giraffplus.storage.entities.UserEntity;

/**
 *
 * @author ExCITE
 */
public class IconRetriever {

    private static final ImageIcon primaryIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/elderNsom32.png"));
    private static final ImageIcon secondaryIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/Giraffdoctor32.png"));
    private static final ImageIcon notDoctorIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/group.png"));
    private static final ImageIcon engineerIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/civil_engineer32.png"));
    private static final ImageIcon houseRobotIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/houserobot48.png"));
    private static final ImageIcon houseNoRobotIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/houseNOrobot48.png"));

    private static final ImageIcon italianFlagIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/Italy-Flag-icon32.png"));
    private static final ImageIcon englishFlagIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/United-Kingdom-flag-icon32.png"));
    private static final ImageIcon swedishFlagIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/sweden_flag_32.png"));
    private static final ImageIcon spanishFlagIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/Spain-Flag-icon32.png"));

    private static final ImageIcon sensorIcon32 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/sensor32.png"));
    private static final ImageIcon alarmIcon32 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/GiraffWarning32.png"));
    public static final ImageIcon alarmIcon16 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/alarm16.png"));
    public static final ImageIcon alarmIcon24 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/alarm24.png"));

    private static final ImageIcon reminderIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/bell48.png"));
    private static final ImageIcon questionIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/questionMark64.png"));
    private static final ImageIcon answerIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/edit48.png"));
    private static final ImageIcon messageIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/callout64.png"));
    public static final ImageIcon messageIcon32 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/callout32.png"));
    
    private static final ImageIcon normalSensor24Icon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/charts24.png"));
    private static final ImageIcon physioSensor24Icon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/diagram24.png"));
    private static final ImageIcon activity24Icon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/chart_pp24.png"));
    
    public static final ImageIcon reminderIcon32 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/postitBad32.png"));
    public static final ImageIcon questionIcon32 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/edit32.png"));
    public static final ImageIcon waitingIcon = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/GiraffLogoFullTSmall2.png"));
    public static final ImageIcon audioMessage32 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/audiocallout32.png"));
    
    public static final ImageIcon oldMan80 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/oldMan80.png"));
    public static final ImageIcon oldWoman80 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/oldWoman80.png"));
    public static final ImageIcon giraffRobot100 = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/GiraffPNGsmaller100.png"));
    
    public static final ImageIcon giraffOFFLINE = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/GiraffPNGsmaller100.png"));
    public static final ImageIcon giraffONLINE = new javax.swing.ImageIcon(IconRetriever.class.getResource("/it/cnr/istc/hsv/images/giraffONLINE.png"));
    
    
    
    
    
    

    public static Icon getHouseIcon(boolean withRobot) {
        return withRobot ? houseRobotIcon : houseNoRobotIcon;
    }

    public static ImageIcon getWaitingIcon() {
        return waitingIcon;
    }

    public static ImageIcon getReminderIcon() {
        return reminderIcon;
    }

    public static ImageIcon getQuestionIcon() {
        return questionIcon;
    }

    public static ImageIcon getAnswerIcon() {
        return answerIcon;
    }

    public static ImageIcon getMessageIcon() {

        return messageIcon;
    }
    

    public static ImageIcon getIcon24ByMainType(MainType type) {
        switch (type) {
            case ACTIVITY:
                return activity24Icon;
            case PHYSIO:
                return physioSensor24Icon;
            case SENSOR:
                return normalSensor24Icon;
            default:
                return null;
        }
    }

    public enum MainType {
        SENSOR,
        PHYSIO,
        ACTIVITY
    }

//    public static Icon getLangLanguage(DVPISSettingsProperties.LANGUAGE lang) {
//        Icon icon = null;
//        switch (lang) {
//            case EN:
//                icon = englishFlagIcon;
//                break;
//            case IT:
//                icon = italianFlagIcon;
//                break;
//            case SE:
//                icon = swedishFlagIcon;
//                break;
//            case ES:
//                icon = spanishFlagIcon;
//                break;
//        }
//        return icon;
//    }

//    public static Icon getIconFromUserType(UserType type) {
//        Icon icon = null;
//        switch (type) {
//            case GP_ENGINEER:
//                icon = engineerIcon;
//                break;
//            case PRIMARY_USER:
//                icon = primaryIcon;
//                break;
//            case SECONDARY_USER:
//                icon = secondaryIcon;
//                break;
//        }
//        return icon;
//    }

//    public static Icon getIconFromUserType(UserEntity user) {
//        StaticProfile staticProfile = ReportManager.getInstance().getCachingAPI().getStaticProfile(user);
//                
//                
//        UserCategory userCategory = staticProfile.getUserCategory();
//        System.out.println("USER CATEGOR-> "+userCategory);
//        if(userCategory == UserCategory.InformalCaregiver){
//            return notDoctorIcon;
//        }
//        if (user.isEngineer() && !user.isPrimary() && !user.isSecondary()) {
//            return engineerIcon;
//        } else if (!user.isEngineer() && user.isPrimary() && !user.isSecondary()) {
//            return primaryIcon;
//        } else if (!user.isEngineer() && !user.isPrimary() && user.isSecondary()) {
//            return secondaryIcon;
//        }
//        return null;
//    }

//      
}
