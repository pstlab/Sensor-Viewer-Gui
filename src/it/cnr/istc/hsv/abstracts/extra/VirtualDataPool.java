/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

//import it.cnr.istc.dvpis.abstracts.SensorTypeClassifier;
//import static it.cnr.istc.dvpis.abstracts.SensorTypeClassifier.SensorTypes.GAP;
//import static it.cnr.istc.dvpis.abstracts.SensorTypeClassifier.SensorTypes.PIR;
//import static it.cnr.istc.dvpis.abstracts.SensorTypeClassifier.SensorTypes.POWER;
//import static it.cnr.istc.dvpis.abstracts.SensorTypeClassifier.SensorTypes.PRESSURE;
//import it.cnr.istc.dvpis.common.flow.StorageAPIManager;
//import it.cnr.istc.dvpis.common.flow.exceptions.InsufficientSSLInfoException;
//import it.cnr.istc.dvpis.common.flow.i18n.I18nTranslator;
//import it.cnr.istc.dvpis.common.flow.settings.DVPISSettings;
//import it.cnr.istc.dvpis.common.flow.wait.WaiterSupporter;
//import it.cnr.istc.dvpis.gui.spot.monitor.enums.SensorProperty;
//import it.cnr.istc.dvpis.gui.spot.monitor.logic.HouseMapper;
//import it.cnr.istc.dvpis.personalization.PersonalizationManager;
//import it.cnr.istc.dvpis.reports.ReportManager;
//import it.cnr.istc.giraffplus.caching.api.Data;
//import it.cnr.istc.giraffplus.pers.api.Event;
import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ERoom;
import it.cnr.istc.hsv.logic.entities.ESensor;
import it.cnr.istc.hsv.logic.entities.ESensorData;
import it.cnr.istc.hsv.logic.entities.EUser;
import java.awt.Point;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JOptionPane;
//import org.bson.types.ObjectId;
import org.w3c.dom.Document;
//import si.xlab.giraffplus.storage.Util;
//import si.xlab.giraffplus.storage.entities.GiraffEntity;
//import si.xlab.giraffplus.storage.entities.HomeEntity;
//import si.xlab.giraffplus.storage.entities.LocationEntity;
//import si.xlab.giraffplus.storage.entities.RoomEntity;
//import si.xlab.giraffplus.storage.entities.SensorData;
//import si.xlab.giraffplus.storage.entities.SensorEntity;
//import si.xlab.giraffplus.storage.entities.UserEntity;

/**
 *
 * @author Luca
 */
public class VirtualDataPool {

    private volatile Map<Long, ESensorData> lastPirDataPerHome = new ConcurrentHashMap<Long, ESensorData>();
    private volatile Map<Long, ESensorData> lastEntranceDoorDataPerHome = new ConcurrentHashMap<Long, ESensorData>();
//    private volatile Map<Long, LocationEntity> giraffRobotPerHome = new ConcurrentHashMap<Long, LocationEntity>();
    private static VirtualDataPool _instance = null;
    private List<EHouse> houses = new ArrayList<EHouse>();
//    private List<UserEntity> followedUsers = new ArrayList<UserEntity>();
    private EUser currentUser = null;
    private boolean superMode = false;
    public static final String BASE_ROOM_LABEL_CODE = "LABEL";
    public static final String BASE_ROOM_LABEL_DELIMITER = ".";
    public static final String BASE_PUPPET_LABEL_CODE = "PUPPET";
    public static final String BASE_PUPPET_LABEL_DELIMITER = ".";
    public static final String BASE_GIRAFF_LABEL_CODE = "GIRAFF";
    public static final String BASE_GIRAFF_LABEL_DELIMITER = ".";
    // pu user id -> home where he lives
    private Map<Long, EHouse> primaryUserHomeMap = new HashMap<Long, EHouse>();
    // house id -> list of user living in the house
    private Map<Long, Collection<EUser>> userInHomeMap = new HashMap<Long, Collection<EUser>>();

    private Map<Long, List<EUser>> networksUserMap = new HashMap<Long, List<EUser>>();
    private Map<Long, Map<String, Document>> activityRulesMap = new HashMap<Long, Map<String, Document>>();
    //       string object id, UserEntity
    private Map<String, EUser> userEntityMap = new HashMap<String, EUser>();
//    // housId -> list of living people ( primary users )
//    private Map<ObjectId, List<WPrimaryUser>> livingUserMap = new HashMap<>();
    // housId -> list of physiological deployed sensors 
//    private Map<ObjectId, List<SensorEntity>> physioSensorsMap = new HashMap<>();

    private Map<Long, ESensor> sensorEntityMap = new HashMap<Long, ESensor>();

    private Map<Long, List<Point>> locationMapPerHouseId = new HashMap<Long, List<Point>>();

    private Map<Long, List<Point>> roomLocationMapPerHouseId = new HashMap<Long, List<Point>>();

    private Map<Long, List<Point>> puppetLocationsMapPerHouseId = new HashMap<Long, List<Point>>();

    private Map<Long, ERoom> roomByLocationIdMap = new HashMap<Long, ERoom>();

    // room id -> location coordinate
//    private Map<Long, LocationEntity> locationsPerRoomId = new HashMap<Long, LocationEntity>();

    //room id  -> puppet location coordinate
    private Map<Long, Point> puppetLocationsPerRoomId = new HashMap<Long, Point>();

    private String certificatePath = "";

    private EHouse currentHouse = null;

    private boolean firstSelectionToAvoid = true;

    private List<HouseSelectionListener> houseSelectionListeners = new ArrayList<HouseSelectionListener>();

//    private Map<ObjectId, Map<HouseControlService.SemaphoreType, HouseControlService.SemaphoreValue>> semaphoreCurrentValuesMap = null;
//    private Map<Long, Event.UserProfile> houseLastProfiles = new HashMap<Long, Event.UserProfile>();

//             Event.UserProfile lastProfile = PersonalizationManager.getInstance().getPersonalizator().getLastProfile(home, userId);
//    private Dictionary dbDictionary = null;
    public static VirtualDataPool getInstance() {
        if (_instance == null) {
            _instance = new VirtualDataPool();
            return _instance;
        } else {
            return _instance;
        }
    }

//    public LocationEntity getDockStationPerHome(Object homeId) {
//        return this.giraffRobotPerHome.get(homeId);
//    }

    public Map<Long, ESensorData> getLastPirDataPerHome() {
        return lastPirDataPerHome;
    }
    
    public void setLastPirDataPerHome(Long houseId, ESensorData data){
        this.lastPirDataPerHome.put(houseId, data);
    }

    public ESensorData getLastEntranceDataPerHome(Long homeId) {
        return lastEntranceDoorDataPerHome.get(homeId);
    }

    public void setLastEntranceDataPerHome(Long homeId, ESensorData data) {
        this.lastEntranceDoorDataPerHome.put(homeId, data);
    }

//    public RoomEntity getRoomByLocationId(LocationEntity entity) {
//        System.out.println(" >>>>>>>>>>>>>>> SEARCHING LOCATION ENTITY: " + entity.getName());
//        return this.roomByLocationIdMap.get(entity.getId());
//    }

    public String buildServiceId() {
        if (!Beans.isDesignTime()) {
            return VirtualDataPool.getInstance().getCurrentUser().getName() + "-" + VirtualDataPool.getInstance().getCurrentUser().getId();
        }
        return "123";
    }

    private VirtualDataPool() {
        super();
    }

    public synchronized void addHouseSelectionListener(HouseSelectionListener listener) {
        this.houseSelectionListeners.add(listener);
    }

    public EHouse getCurrentHouse() {
        return currentHouse;
    }

    public synchronized void setCurrentHouse(EHouse currentHouse) {
        if (firstSelectionToAvoid) {
            firstSelectionToAvoid = false;
            return;
        }
        this.currentHouse = currentHouse;
//        PersonalizationManager.getInstance().getConnectionManagerAPI().setCurrentHome(currentHouse);
        for (HouseSelectionListener houseSelectionListener : houseSelectionListeners) {
            houseSelectionListener.houseChanged(currentHouse);
        }
    }

    public synchronized List<Point> getLocationsPerHouseId(Long houseId) {
        if (this.locationMapPerHouseId.containsKey(houseId)) {
            return locationMapPerHouseId.get(houseId);
        } else {
            return null;
        }
    }

    public synchronized List<Point> getRoomLocationsPerHouseId(Long houseId) {
        if (this.roomLocationMapPerHouseId.containsKey(houseId)) {
            return roomLocationMapPerHouseId.get(houseId);
        } else {
            return null;
        }
    }

    public synchronized List<Point> getPuppetLocationsPerHouseId(Long houseId) {
        if (this.puppetLocationsMapPerHouseId.containsKey(houseId)) {
            return puppetLocationsMapPerHouseId.get(houseId);
        } else {
            return null;
        }
    }

    public synchronized void mapLocationOfThisHouse(EHouse house) {
        System.out.println("MAPPING G G G HOUSE: " + house.getName());
//        List<LocationEntity> locations = house.getLocations();
        List<ERoom> rooms = house.getRooms();
        System.out.println("+++++++++++++++++++++++++++++++++++++ MAPPING HOUSE: " + house.getName() + " +++++++++++++++++++++++++");
//        for (Room roomEntity : rooms) {
//            System.out.println("ROOM -> " + roomEntity.getName());
//            List<ObjectId> locationsByRoom = roomEntity.getLocations();
////            System.out.println("èèèèèèèèèèèèèèèèèèèèèèèèèèèèè locations !! ");
//            for (ObjectId objectId : locationsByRoom) {
//                LocationEntity location = ReportManager.getInstance().getCachingAPI().getLocation(objectId);
//                System.out.println("-> location is: " + location.getName());
//                this.roomByLocationIdMap.put(objectId, roomEntity);
//            }
////            System.out.println("èèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèè");
////        }
//        if (!this.locationMapPerHouseId.containsKey(house.getId())) {
//            this.locationMapPerHouseId.put(house.getId(), new ArrayList<LocationEntity>());
//            this.locationMapPerHouseId.get(house.getId()).addAll(locations);
//        }
//        if (DVPISSettings.getInstance().isVerbose()) {
//            System.out.println("[dvpis.office] locations mapped");
//        }

    }

//    public LocationEntity getLabelLocationByRoomId(ObjectId roomID) {
//        return locationsPerRoomId.get(roomID);
//    }

//    public synchronized void checkAndFixRoomLabels(HomeEntity house) {
//        if (DVPISSettings.getInstance().isVerbose()) {
//            System.out.println("[dvpis@office] <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< CHEK AND FIX ROOM LABELS <<<<<<<<<<<<<<<");
//        }
//        List<LocationEntity> locations = house.getLocations();
////        JOptionPane.showMessageDialog(null, "Size of locations: "+locations.size());
//
//        List<LocationEntity> eventualNewLocations = new ArrayList<LocationEntity>();
//
//        if (!this.roomLocationMapPerHouseId.containsKey(house.getId())) {
//            this.roomLocationMapPerHouseId.put(house.getId(), new ArrayList<LocationEntity>());
//        }
//        if (!this.puppetLocationsMapPerHouseId.containsKey(house.getId())) {
//            this.puppetLocationsMapPerHouseId.put(house.getId(), new ArrayList<LocationEntity>());
//        }
//        Map<String, LocationEntity> namedLocationMap = new HashMap<String, LocationEntity>();
//        List<RoomEntity> rooms = house.getRooms();
//        for (LocationEntity locationEntity : locations) {
//            namedLocationMap.put(locationEntity.getName(), locationEntity);
//        }
//        for (RoomEntity room : rooms) {
//            if (DVPISSettings.getInstance().isVerbose()) {
//                System.out.println("[dvpis@office] -- checking room: " + room.getName());
//            }
//            if (!namedLocationMap.containsKey(BASE_ROOM_LABEL_CODE + BASE_ROOM_LABEL_DELIMITER + room.getName())) {
//                LocationEntity labelRoomLocation = new LocationEntity();
//                labelRoomLocation.setName(BASE_ROOM_LABEL_CODE + BASE_ROOM_LABEL_DELIMITER + room.getName());
//                labelRoomLocation.setX(0d);
//                labelRoomLocation.setY(0d);
//                eventualNewLocations.add(labelRoomLocation);
//                house.pushLocation(labelRoomLocation);
//                locationsPerRoomId.put(room.getId(), labelRoomLocation);
//                roomLocationMapPerHouseId.get(house.getId()).add(labelRoomLocation);
//                namedLocationMap.put(BASE_ROOM_LABEL_CODE + BASE_ROOM_LABEL_DELIMITER + room.getName(),labelRoomLocation );
//            } else {
//                locationsPerRoomId.put(room.getId(), namedLocationMap.get(BASE_ROOM_LABEL_CODE + BASE_ROOM_LABEL_DELIMITER + room.getName()));
//                this.roomLocationMapPerHouseId.get(house.getId()).add(namedLocationMap.get(BASE_ROOM_LABEL_CODE + BASE_ROOM_LABEL_DELIMITER + room.getName()));
//            }
//            roomByLocationIdMap.put(namedLocationMap.get(BASE_ROOM_LABEL_CODE + BASE_ROOM_LABEL_DELIMITER + room.getName()).getId(), room);
//            // CHECKING PUPPETS
//
//            if (!namedLocationMap.containsKey(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId())) {
//                LocationEntity labelPuppetLocation = new LocationEntity();
//                labelPuppetLocation.setName(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId());
//                labelPuppetLocation.setX(0d);
//                labelPuppetLocation.setY(0d);
//                eventualNewLocations.add(labelPuppetLocation);
//                house.pushLocation(labelPuppetLocation);
//                puppetLocationsPerRoomId.put(room.getId(), labelPuppetLocation);
//                this.puppetLocationsMapPerHouseId.get(house.getId()).add(namedLocationMap.get(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId()));
//            } else {
//                puppetLocationsPerRoomId.put(room.getId(), namedLocationMap.get(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId()));
//                this.puppetLocationsMapPerHouseId.get(house.getId()).add(namedLocationMap.get(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId()));
//            }
//        }
//        if (house.getGiraffs() != null && !house.getGiraffs().isEmpty()) {
//            GiraffEntity giraff = house.getGiraffs().get(0);
//            if (!namedLocationMap.containsKey(BASE_GIRAFF_LABEL_CODE + BASE_GIRAFF_LABEL_DELIMITER + giraff.getName() + BASE_GIRAFF_LABEL_DELIMITER + giraff.getId())) {
//                LocationEntity dockStationLocation = new LocationEntity();
//                dockStationLocation.setX(0d);
//                dockStationLocation.setY(0d);
//                dockStationLocation.setName(BASE_GIRAFF_LABEL_CODE + BASE_GIRAFF_LABEL_DELIMITER + giraff.getName() + BASE_GIRAFF_LABEL_DELIMITER + giraff.getId());
//                eventualNewLocations.add(dockStationLocation);
//                house.pushLocation(dockStationLocation);
//
//                System.out.println("[dvpis@office] creating and updating house with a new Giraff Dock Location");
//                this.giraffRobotPerHome.put(house.getId(), dockStationLocation);
//            } else {
//                System.out.println("[dvpis@office] giraff dock station location found");
//                this.giraffRobotPerHome.put(house.getId(), namedLocationMap.get(BASE_GIRAFF_LABEL_CODE + BASE_GIRAFF_LABEL_DELIMITER + giraff.getName() + BASE_GIRAFF_LABEL_DELIMITER + giraff.getId()));
//            }
//
//        }
//        if (!eventualNewLocations.isEmpty()) {
//
////            this.roomLocationMapPerHouseId.get(house.getId()).addAll(eventualNewLocations);
//            try {
//                StorageAPIManager.getInstance().getStorage().updateHomeEntity(house, null);
//                house.setVersion(house.getVersion() + 1);
//            } catch (InsufficientSSLInfoException ex) {
//                ex.printStackTrace();
//                Logger.getLogger(VirtualDataPool.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (Util.GiraffPlusStorageAPIException ex) {
//                ex.printStackTrace();
//                Logger.getLogger(VirtualDataPool.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if (DVPISSettings.getInstance().isVerbose()) {
//            System.out.println("[dvpis@office]<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  E N D     -  CHEK AND FIX ROOM LABELS <<<<<<<<<<<<<<<");
//        }
////        JOptionPane.showConfirmDialog(null, "FATTO");
//
//    }

    public Icon getSensorTypeIconBySensor(ESensor sensor) {
        String unit = sensor.getSensorType().getTypeUnit();
        if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.FALLEN_ALARM) {
            return SensorProperty.ALARM_ICON.getIcon();
        } else if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.FLOODING_ALARM) {
            return SensorProperty.ALARM_ICON.getIcon();
        } else if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.CO_ALARM) {
            return SensorProperty.ALARM_ICON.getIcon();
        } else {
            return SensorProperty.STATUS_OK.getIcon();
        }
    }

    private void loadDBDictionary() {

//        try {
//            I18nTranslator.getInstance().addRemoteValues(StorageAPIManager.getInstance().getStorage());
////            dbDictionary = StorageAPIManager.getInstance().getStorage().downloadProperties(I18nTranslator.getInstance().getCurrentLanguage().name().toLowerCase());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Logger.getLogger(VirtualDataPool.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        catch (Util.GiraffPlusStorageAPIException ex) {
//            Logger.getLogger(VirtualDataPool.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        catch (Util.GiraffPlusStorageAPIException ex) {
//            Logger.getLogger(VirtualDataPool.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
//
//    public Dictionary getDbDictionary() {
//        return dbDictionary;
//    }

    public Icon getHouseMapSensorTypeIconBySensor(EHouse home, ESensor sensor, boolean initial) {
        String type = sensor.getSensorType().getName();

        String unit = sensor.getSensorType().getMeaning();   //DUBBIO
        if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.MULTI_TYPE) {
            String loc = sensor.getSensorType().getMeaning();
            if (loc.toLowerCase().contains("lamp")) {
                return initial ? SensorProperty.INIT_LAMP_ICON.getIcon() : SensorProperty.LAMP_OFF_ICON.getIcon();
            }
        }
        if (SensorTypeClassifier.isSensorAnAlarm(sensor.getSensorType().getId())) {
            return initial ? SensorProperty.INIT_ALARM_ICON.getIcon() : SensorProperty.ALARM_ICON.getIcon();
        } else {
            SensorTypeClassifier.SensorTypes typeEnum = SensorTypeClassifier.getTypeFromSensorType(sensor.getSensorType());
            if (typeEnum == null) {
                return SensorProperty.GENERIC_SENSOR_OFF.getIcon();
            }
            switch (typeEnum) {
                case GAP:
//                    ObjectId locationId = sensor.getLocation();
                     
                    String loc = sensor.getLocation();
                    System.out.println("----- >  EXAMINING : " +loc);
                    if (loc.toLowerCase().contains("fridge")) {
                        return initial ? SensorProperty.INIT_RIDGE_CLOSED.getIcon() : SensorProperty.FRIDGE_CLOSED.getIcon();
                    }
                    if (loc.toLowerCase().contains("wardrobe")) {
                        return initial ? SensorProperty.INIT_WARDROBE_CLOSED.getIcon() : SensorProperty.WARDROBE_CLOSED.getIcon();
                    }
                    if (loc.toLowerCase().contains("window")) {
                        return initial ? SensorProperty.INIT_WINDOW_CLOSED.getIcon() : SensorProperty.WINDOW_CLOSED.getIcon();
                    }
                    return initial ? SensorProperty.INIT_GAP_CLOSED_ICON.getIcon() : SensorProperty.GAP_CLOSED_ICON.getIcon();
                case PIR:
                    return initial ? SensorProperty.INIT_PIR_MOVE_ICON.getIcon() : SensorProperty.PIR_NOT_MOVE_ICON.getIcon();
                case POWER:
                    return initial ? SensorProperty.INIT_ELECTRICITY_ON_ICON.getIcon() : SensorProperty.ELECTRICITY_OFF_ICON.getIcon();
                case PRESSURE:
                    return initial ? SensorProperty.INIT_PRESSURE_ON_ICON.getIcon() : SensorProperty.PRESSURE_OFF_ICON.getIcon();

            }
        }

        return SensorProperty.STATUS_OK.getIcon();
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public EUser getCurrentUser() {
        return currentUser;
    }
//
//    public UserEntity getUserEntityByID(String id) {
//        if (!userEntityMap.containsKey(id)) {
////            try {
//            UserEntity user = ReportManager.getInstance().getCachingAPI().getUser(new ObjectId(id));
////                UserEntity userEntity = StorageAPIManager.getInstance().getStorage().getUserEntity(new ObjectId(id));
//            userEntityMap.put(id, user);
////            } catch (InsufficientSSLInfoException ex) {
////                ex.printStackTrace();
////                Logger.getLogger(VirtualDataPool.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (Util.GiraffPlusStorageAPIException ex) {
////                ex.printStackTrace();
////                Logger.getLogger(VirtualDataPool.class.getName()).log(Level.SEVERE, null, ex);
////            }
//        }
//        return userEntityMap.get(id);
//    }

//    public Document getRulesByActivity(ObjectId houseId, String activity) {
//        if (activityRulesMap.containsKey(houseId)) {
//            return this.activityRulesMap.get(houseId).get(activity);
//        }
//        return null;
//    }

//    public synchronized void setRulesByActivity(ObjectId houseId, String activity, Document rule) {
//        if (!activityRulesMap.containsKey(houseId)) {
//            this.activityRulesMap.put(houseId, new HashMap<String, Document>());
//        }
//        this.activityRulesMap.get(houseId).put(activity, rule);
//    }
//
//    public synchronized boolean isRulesAlreadyLoaded(ObjectId houseId, String activity) {
//        if (!activityRulesMap.containsKey(houseId)) {
//            return false;
//        }
//        return activityRulesMap.get(houseId).containsKey(activity);
//    }

    public boolean isSuperMode() {
//        return false;  // TEST MODE
        return superMode;
    }

    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }

    public synchronized void setCurrentUser(EUser currentUser) {
        try {
            this.currentUser = currentUser;
//            if (this.currentUser.isEngineer()) {
//                this.setSuperMode(true);
//            } else {
//                this.setSuperMode(false);
//            }
        } catch (NullPointerException nu) {
            nu.printStackTrace();
//            if (DVPISSettings.getInstance().isVerbose()) {
//                System.out.println("[dvpis.office] people.discovery service is not available");
//            }
        }
    }

    public ESensor getSensorEntityById(Long id) {
        return sensorEntityMap.get(id);
    }

    public synchronized void initAll() throws Exception {
//        System.out.println("[DVPIS@Office] Responsabilities: " + currentUser.getResponsibilities());
//        followedUsers = StorageAPIManager.getInstance().getStorage().getUserEntitiesByIds(currentUser.getResponsibilities());

//        System.out.println("CURRENT USER = " + currentUser.getName());
//
//        Collection<HomeEntity> homes = ReportManager.getInstance().getCachingAPI().getHomes(currentUser);
//        System.out.println("HOMES SIZE = " + homes.size());
//        houses.addAll(homes);
//        for (HomeEntity homeEntity : homes) {
//            System.out.println("mapping locations of house: " + homeEntity);
//            mapLocationOfThisHouse(homeEntity);
//        }
////        houses = StorageAPIManager.getInstance().getStorage().getHomeListForSecondaryUser(this.currentUser.getId());
//
//        Collections.sort(houses, new Comparator<HomeEntity>() {
//
//            @Override
//            public int compare(HomeEntity o1, HomeEntity o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
//
//        Thread t = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                WaiterSupporter.getInstance().getSplasher().start();
//            }
//        });
//        t.start();
//        for (final HomeEntity home : houses) {
//            Thread ts = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    WaiterSupporter.getInstance().getSplasher().publish("Loading PerS semaphores status [house: " + home.getName() + "]");
//                }
//            });
//            ts.start();
//            Event.UserProfile lastProfile = PersonalizationManager.getInstance().getPersonalizator().getLastProfile(home, home.getPrimaryUsers().get(0));
//            this.houseLastProfiles.put(home.getId(), lastProfile);
//            Thread ts2 = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    WaiterSupporter.getInstance().getSplasher().publish("Loading completed.");
//                }
//            });
//            ts2.start();
//            Collection<UserEntity> inhabitants = ReportManager.getInstance().getCachingAPI().getPrimaryUsers(home);
////            List<UserEntity> inhabitants = StorageAPIManager.getInstance().getStorage().getUserEntitiesByIds(home.getPrimaryUsers());
//            for (UserEntity uuu : inhabitants) {
//                this.primaryUserHomeMap.put(uuu.getId(), home);
//            }
//            this.userInHomeMap.put(home.getId(), inhabitants);
//            HouseMapper.mapHouse(home);
////            physioSensorsMap.put(home.getId(), new ArrayList<SensorEntity>());
//            List<SensorEntity> sensors = home.getSensors();
//            for (SensorEntity sensor : sensors) {
//                sensorEntityMap.put(sensor.getId(), sensor);
//            }
//            WaiterSupporter.getInstance().getSplasher().stop();
//
//        }
//        Thread ts3 = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                WaiterSupporter.getInstance().getSplasher().publish("[i18n] loading remote dictionary..");
//            }
//        });
//        ts3.start();
////        for (UserEntity u : followedUsers) {
////            List<ObjectId> social_networking = u.getSocial_networking();
////            List<UserEntity> userEntitiesByIds = StorageAPIManager.getInstance().getStorage().getUserEntitiesByIds(social_networking);
////            this.networksUserMap.put(u.getId(), userEntitiesByIds);
////        }
////        WaiterSupporter.getInstance().getSplasher().start();
//
//        loadDBDictionary();
////        WaiterSupporter.getInstance().getSplasher().publish("[i18n] remote dictionary has been loaded");
////        WaiterSupporter.getInstance().getSplasher().stop();
//        Thread ts3l = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                WaiterSupporter.getInstance().getSplasher().publish("[i18n] remote dictionary has been loaded");
//                WaiterSupporter.getInstance().getSplasher().stop();
//            }
//        });
//        ts3l.start();
//        if (DVPISSettings.getInstance().isVerbose()) {
//            System.out.println("Dictionary Loaded");
//        }

    }

//    public synchronized Event.UserProfile getLastProfileByHome(HomeEntity home) {
//        return this.houseLastProfiles.get(home.getId());
//    }

    /**
     * Rerutn the list of user living in a Home by passing to the argument the
     * house id.
     *
     * @param houseId ObjectId of the house.
     * @return
     */
//    public synchronized List<UserEntity> getLivingUsers(ObjectId houseId) {
//        List<UserEntity> pus = new ArrayList<UserEntity>();
//        pus.addAll(Collections.unmodifiableCollection(this.userInHomeMap.get(houseId)));
//        return pus;
//    }

//    public List<SensorEntity> getPhysiologicalSensorByHouse(ObjectId id){
//        return this.physioSensorsMap.get(id);
//    }
//    public List<SensorEntity> getSensorsByHouseId(ObjectId houseId) {
//        return Collections.unmodifiableList(.get(houseId));
//    }
    public List<EHouse> getHouses() {
        return Collections.unmodifiableList(houses);
    }

//    public void setFollowedUsers(List<WPrimaryUser> followedUsers) {
//        this.followedUsers = followedUsers;
//    }
//    public List<UserEntity> getFollowedUsers() {
//        return Collections.unmodifiableList(followedUsers);
//    }
//    public void mapNetwork(ObjectId primaryId, List<UserEntity> network) {
//        this.networksUserMap.put(primaryId, network);
//    }
//    public List<UserEntity> getNetwork(ObjectId primaryId) {
//        return new ArrayList<UserEntity>();//this.networksUserMap.get(primaryId);
//    }

    public Icon getValueHouseMapSensorTypeIcon(EHouse home, ESensor sensor, boolean value) {
        String type = sensor.getSensorType().getName();

        if (SensorTypeClassifier.isSensorAnAlarm(sensor.getSensorType().getId())) {
            return SensorProperty.ALARM_ICON.getIcon();
        } else {
            SensorTypeClassifier.SensorTypes typeEnum = SensorTypeClassifier.getTypeFromSensorType(sensor.getSensorType());
            if (typeEnum == null) {
                return value ? SensorProperty.GENERIC_SENSOR_ON.getIcon() : SensorProperty.GENERIC_SENSOR_OFF.getIcon();
            }
            switch (typeEnum) {
                case GAP:
                   // LocationEntity loc = home.getLocation(sensor.getLocation());
                    String loc = sensor.getLocation();
                    System.out.println("----- >  EXAMINING : " +loc);
                    if (loc.toLowerCase().contains("fridge")) {
                        return value ? SensorProperty.FRIDGE_OPEN.getIcon() : SensorProperty.FRIDGE_CLOSED.getIcon();
                    }
                    if (loc.toLowerCase().contains("wardrobe")) {
                        return value ? SensorProperty.WARDROBE_OPEN.getIcon() : SensorProperty.WARDROBE_CLOSED.getIcon();
                    }
                    if (loc.toLowerCase().contains("window")) {
                        return value ? SensorProperty.WINDOW_OPENED.getIcon() : SensorProperty.WINDOW_CLOSED.getIcon();
                    }
                    return value ? SensorProperty.GAP_OPEN_ICON.getIcon() : SensorProperty.GAP_CLOSED_ICON.getIcon();
                case PIR:
                    return value ? SensorProperty.PIR_MOVE_ICON.getIcon() : SensorProperty.PIR_NOT_MOVE_ICON.getIcon();
                case POWER:
                    return value ? SensorProperty.ELECTRICITY_ON_ICON.getIcon() : SensorProperty.ELECTRICITY_OFF_ICON.getIcon();
                case PRESSURE:
                    return value ? SensorProperty.PRESSURE_ON_ICON.getIcon() : SensorProperty.PRESSURE_OFF_ICON.getIcon();

            }
        }

        return SensorProperty.STATUS_OK.getIcon();
    }

    public interface HouseSelectionListener {

        public void houseChanged(EHouse house);
    }

}
