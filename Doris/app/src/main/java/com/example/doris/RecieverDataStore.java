package com.example.doris;

public class RecieverDataStore {


    public RecieverDataStore(String gps_status, String direction, String engine_temp, String ignition, String lat, String lon, String lights, String odometer, String speedOfVehicle, String tire_pressure, String track_time, String ABS_Warning_Request, String battery_Warning, String boot_Door_Warning, String distance_Totalizer, String driver_Door_Locked, String engine_Coolant_Temperature, String engine_RPM, String front_Left_Door_Warning, String front_Right_Door_Warning, String fuel_Low, String high_Beam, String longitudinal_Acceleration, String low_Beam, String mean_Effective_Torque, String parking_Brake, String raw_Sensor, String rear_Left_Door_Warning, String rear_Right_Door_Warning, String seat_Belt_2nd_Row_Center_Status, String seat_Belt_2nd_Row_Left_Status, String seat_Belt_2nd_Row_Right_Status, String seat_Belt_Driver_Reminder, String seat_Belt_Front_Passenger_Reminder, String speed_Wheel_Front_Left, String speed_Wheel_Front_Right, String speed_Wheel_Rear_Left, String speed_Wheel_Rear_Right, String steering_Wheel_Angle, String steering_Wheel_Angle_Offset, String steering_Wheel_Rotation_Speed, String transversal_Acceleration, String vehicleBatteryVoltage, String vehicle_Speed, String warning_Lights, String internalbattvolts, String action, String appName, String appName_headUnit, String BT_Connectivity_Status, String categoryName, String categoryName_headUnit, String end_time_for_BT_connectivity, String end_time_on_Channel, String frequency_of_channel, String keyType, String screenName, String start_time_for_BT_connectivity, String start_time_on_Channel, String state, String time_BT_is_connected, String time_Spent_on_the_Channel, String direction_headUnit, String key, String programme_identification, String programme_type, String source, String source_headUnit, String stationName, String waveband) {
        Gps_status = gps_status;
        this.direction = direction;
        this.engine_temp = engine_temp;
        this.ignition = ignition;
        this.lat = lat;
        this.lon = lon;
        this.lights = lights;
        this.odometer = odometer;
        this.speedOfVehicle = speedOfVehicle;
        this.tire_pressure = tire_pressure;
        this.track_time = track_time;
        this.ABS_Warning_Request = ABS_Warning_Request;
        Battery_Warning = battery_Warning;
        Boot_Door_Warning = boot_Door_Warning;
        Distance_Totalizer = distance_Totalizer;
        Driver_Door_Locked = driver_Door_Locked;
        Engine_Coolant_Temperature = engine_Coolant_Temperature;
        Engine_RPM = engine_RPM;
        Front_Left_Door_Warning = front_Left_Door_Warning;
        Front_Right_Door_Warning = front_Right_Door_Warning;
        Fuel_Low = fuel_Low;
        High_Beam = high_Beam;
        Longitudinal_Acceleration = longitudinal_Acceleration;
        Low_Beam = low_Beam;
        Mean_Effective_Torque = mean_Effective_Torque;
        Parking_Brake = parking_Brake;
        Raw_Sensor = raw_Sensor;
        Rear_Left_Door_Warning = rear_Left_Door_Warning;
        Rear_Right_Door_Warning = rear_Right_Door_Warning;
        Seat_Belt_2nd_Row_Center_Status = seat_Belt_2nd_Row_Center_Status;
        Seat_Belt_2nd_Row_Left_Status = seat_Belt_2nd_Row_Left_Status;
        Seat_Belt_2nd_Row_Right_Status = seat_Belt_2nd_Row_Right_Status;
        Seat_Belt_Driver_Reminder = seat_Belt_Driver_Reminder;
        Seat_Belt_Front_Passenger_Reminder = seat_Belt_Front_Passenger_Reminder;
        Speed_Wheel_Front_Left = speed_Wheel_Front_Left;
        Speed_Wheel_Front_Right = speed_Wheel_Front_Right;
        Speed_Wheel_Rear_Left = speed_Wheel_Rear_Left;
        Speed_Wheel_Rear_Right = speed_Wheel_Rear_Right;
        Steering_Wheel_Angle = steering_Wheel_Angle;
        Steering_Wheel_Angle_Offset = steering_Wheel_Angle_Offset;
        Steering_Wheel_Rotation_Speed = steering_Wheel_Rotation_Speed;
        Transversal_Acceleration = transversal_Acceleration;
        VehicleBatteryVoltage = vehicleBatteryVoltage;
        Vehicle_Speed = vehicle_Speed;
        Warning_Lights = warning_Lights;
        this.internalbattvolts = internalbattvolts;
        Action = action;
        AppName = appName;
        AppName_headUnit = appName_headUnit;
        this.BT_Connectivity_Status = BT_Connectivity_Status;
        CategoryName = categoryName;
        CategoryName_headUnit = categoryName_headUnit;
        End_time_for_BT_connectivity = end_time_for_BT_connectivity;
        End_time_on_Channel = end_time_on_Channel;
        Frequency_of_channel = frequency_of_channel;
        KeyType = keyType;
        ScreenName = screenName;
        Start_time_for_BT_connectivity = start_time_for_BT_connectivity;
        Start_time_on_Channel = start_time_on_Channel;
        State = state;
        Time_BT_is_connected = time_BT_is_connected;
        Time_Spent_on_the_Channel = time_Spent_on_the_Channel;
        this.direction_headUnit = direction_headUnit;
        this.key = key;
        this.programme_identification = programme_identification;
        this.programme_type = programme_type;
        this.source = source;
        this.source_headUnit = source_headUnit;
        this.stationName = stationName;
        this.waveband = waveband;
    }

    public String getGps_status() {
        return Gps_status;
    }

    public void setGps_status(String gps_status) {
        Gps_status = gps_status;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEngine_temp() {
        return engine_temp;
    }

    public void setEngine_temp(String engine_temp) {
        this.engine_temp = engine_temp;
    }

    public String getIgnition() {
        return ignition;
    }

    public void setIgnition(String ignition) {
        this.ignition = ignition;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLights() {
        return lights;
    }

    public void setLights(String lights) {
        this.lights = lights;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getSpeedOfVehicle() {
        return speedOfVehicle;
    }

    public void setSpeedOfVehicle(String speedOfVehicle) {
        this.speedOfVehicle = speedOfVehicle;
    }

    public String getTire_pressure() {
        return tire_pressure;
    }

    public void setTire_pressure(String tire_pressure) {
        this.tire_pressure = tire_pressure;
    }

    public String getTrack_time() {
        return track_time;
    }

    public void setTrack_time(String track_time) {
        this.track_time = track_time;
    }

    public String getABS_Warning_Request() {
        return ABS_Warning_Request;
    }

    public void setABS_Warning_Request(String ABS_Warning_Request) {
        this.ABS_Warning_Request = ABS_Warning_Request;
    }

    public String getBattery_Warning() {
        return Battery_Warning;
    }

    public void setBattery_Warning(String battery_Warning) {
        Battery_Warning = battery_Warning;
    }

    public String getBoot_Door_Warning() {
        return Boot_Door_Warning;
    }

    public void setBoot_Door_Warning(String boot_Door_Warning) {
        Boot_Door_Warning = boot_Door_Warning;
    }

    public String getDistance_Totalizer() {
        return Distance_Totalizer;
    }

    public void setDistance_Totalizer(String distance_Totalizer) {
        Distance_Totalizer = distance_Totalizer;
    }

    public String getDriver_Door_Locked() {
        return Driver_Door_Locked;
    }

    public void setDriver_Door_Locked(String driver_Door_Locked) {
        Driver_Door_Locked = driver_Door_Locked;
    }

    public String getEngine_Coolant_Temperature() {
        return Engine_Coolant_Temperature;
    }

    public void setEngine_Coolant_Temperature(String engine_Coolant_Temperature) {
        Engine_Coolant_Temperature = engine_Coolant_Temperature;
    }

    public String getEngine_RPM() {
        return Engine_RPM;
    }

    public void setEngine_RPM(String engine_RPM) {
        Engine_RPM = engine_RPM;
    }

    public String getFront_Left_Door_Warning() {
        return Front_Left_Door_Warning;
    }

    public void setFront_Left_Door_Warning(String front_Left_Door_Warning) {
        Front_Left_Door_Warning = front_Left_Door_Warning;
    }

    public String getFront_Right_Door_Warning() {
        return Front_Right_Door_Warning;
    }

    public void setFront_Right_Door_Warning(String front_Right_Door_Warning) {
        Front_Right_Door_Warning = front_Right_Door_Warning;
    }

    public String getFuel_Low() {
        return Fuel_Low;
    }

    public void setFuel_Low(String fuel_Low) {
        Fuel_Low = fuel_Low;
    }

    public String getHigh_Beam() {
        return High_Beam;
    }

    public void setHigh_Beam(String high_Beam) {
        High_Beam = high_Beam;
    }

    public String getLongitudinal_Acceleration() {
        return Longitudinal_Acceleration;
    }

    public void setLongitudinal_Acceleration(String longitudinal_Acceleration) {
        Longitudinal_Acceleration = longitudinal_Acceleration;
    }

    public String getLow_Beam() {
        return Low_Beam;
    }

    public void setLow_Beam(String low_Beam) {
        Low_Beam = low_Beam;
    }

    public String getMean_Effective_Torque() {
        return Mean_Effective_Torque;
    }

    public void setMean_Effective_Torque(String mean_Effective_Torque) {
        Mean_Effective_Torque = mean_Effective_Torque;
    }

    public String getParking_Brake() {
        return Parking_Brake;
    }

    public void setParking_Brake(String parking_Brake) {
        Parking_Brake = parking_Brake;
    }

    public String getRaw_Sensor() {
        return Raw_Sensor;
    }

    public void setRaw_Sensor(String raw_Sensor) {
        Raw_Sensor = raw_Sensor;
    }

    public String getRear_Left_Door_Warning() {
        return Rear_Left_Door_Warning;
    }

    public void setRear_Left_Door_Warning(String rear_Left_Door_Warning) {
        Rear_Left_Door_Warning = rear_Left_Door_Warning;
    }

    public String getRear_Right_Door_Warning() {
        return Rear_Right_Door_Warning;
    }

    public void setRear_Right_Door_Warning(String rear_Right_Door_Warning) {
        Rear_Right_Door_Warning = rear_Right_Door_Warning;
    }

    public String getSeat_Belt_2nd_Row_Center_Status() {
        return Seat_Belt_2nd_Row_Center_Status;
    }

    public void setSeat_Belt_2nd_Row_Center_Status(String seat_Belt_2nd_Row_Center_Status) {
        Seat_Belt_2nd_Row_Center_Status = seat_Belt_2nd_Row_Center_Status;
    }

    public String getSeat_Belt_2nd_Row_Left_Status() {
        return Seat_Belt_2nd_Row_Left_Status;
    }

    public void setSeat_Belt_2nd_Row_Left_Status(String seat_Belt_2nd_Row_Left_Status) {
        Seat_Belt_2nd_Row_Left_Status = seat_Belt_2nd_Row_Left_Status;
    }

    public String getSeat_Belt_2nd_Row_Right_Status() {
        return Seat_Belt_2nd_Row_Right_Status;
    }

    public void setSeat_Belt_2nd_Row_Right_Status(String seat_Belt_2nd_Row_Right_Status) {
        Seat_Belt_2nd_Row_Right_Status = seat_Belt_2nd_Row_Right_Status;
    }

    public String getSeat_Belt_Driver_Reminder() {
        return Seat_Belt_Driver_Reminder;
    }

    public void setSeat_Belt_Driver_Reminder(String seat_Belt_Driver_Reminder) {
        Seat_Belt_Driver_Reminder = seat_Belt_Driver_Reminder;
    }

    public String getSeat_Belt_Front_Passenger_Reminder() {
        return Seat_Belt_Front_Passenger_Reminder;
    }

    public void setSeat_Belt_Front_Passenger_Reminder(String seat_Belt_Front_Passenger_Reminder) {
        Seat_Belt_Front_Passenger_Reminder = seat_Belt_Front_Passenger_Reminder;
    }

    public String getSpeed_Wheel_Front_Left() {
        return Speed_Wheel_Front_Left;
    }

    public void setSpeed_Wheel_Front_Left(String speed_Wheel_Front_Left) {
        Speed_Wheel_Front_Left = speed_Wheel_Front_Left;
    }

    public String getSpeed_Wheel_Front_Right() {
        return Speed_Wheel_Front_Right;
    }

    public void setSpeed_Wheel_Front_Right(String speed_Wheel_Front_Right) {
        Speed_Wheel_Front_Right = speed_Wheel_Front_Right;
    }

    public String getSpeed_Wheel_Rear_Left() {
        return Speed_Wheel_Rear_Left;
    }

    public void setSpeed_Wheel_Rear_Left(String speed_Wheel_Rear_Left) {
        Speed_Wheel_Rear_Left = speed_Wheel_Rear_Left;
    }

    public String getSpeed_Wheel_Rear_Right() {
        return Speed_Wheel_Rear_Right;
    }

    public void setSpeed_Wheel_Rear_Right(String speed_Wheel_Rear_Right) {
        Speed_Wheel_Rear_Right = speed_Wheel_Rear_Right;
    }

    public String getSteering_Wheel_Angle() {
        return Steering_Wheel_Angle;
    }

    public void setSteering_Wheel_Angle(String steering_Wheel_Angle) {
        Steering_Wheel_Angle = steering_Wheel_Angle;
    }

    public String getSteering_Wheel_Angle_Offset() {
        return Steering_Wheel_Angle_Offset;
    }

    public void setSteering_Wheel_Angle_Offset(String steering_Wheel_Angle_Offset) {
        Steering_Wheel_Angle_Offset = steering_Wheel_Angle_Offset;
    }

    public String getSteering_Wheel_Rotation_Speed() {
        return Steering_Wheel_Rotation_Speed;
    }

    public void setSteering_Wheel_Rotation_Speed(String steering_Wheel_Rotation_Speed) {
        Steering_Wheel_Rotation_Speed = steering_Wheel_Rotation_Speed;
    }

    public String getTransversal_Acceleration() {
        return Transversal_Acceleration;
    }

    public void setTransversal_Acceleration(String transversal_Acceleration) {
        Transversal_Acceleration = transversal_Acceleration;
    }

    public String getVehicleBatteryVoltage() {
        return VehicleBatteryVoltage;
    }

    public void setVehicleBatteryVoltage(String vehicleBatteryVoltage) {
        VehicleBatteryVoltage = vehicleBatteryVoltage;
    }

    public String getVehicle_Speed() {
        return Vehicle_Speed;
    }

    public void setVehicle_Speed(String vehicle_Speed) {
        Vehicle_Speed = vehicle_Speed;
    }

    public String getWarning_Lights() {
        return Warning_Lights;
    }

    public void setWarning_Lights(String warning_Lights) {
        Warning_Lights = warning_Lights;
    }

    public String getInternalbattvolts() {
        return internalbattvolts;
    }

    public void setInternalbattvolts(String internalbattvolts) {
        this.internalbattvolts = internalbattvolts;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppName_headUnit() {
        return AppName_headUnit;
    }

    public void setAppName_headUnit(String appName_headUnit) {
        AppName_headUnit = appName_headUnit;
    }

    public String getBT_Connectivity_Status() {
        return BT_Connectivity_Status;
    }

    public void setBT_Connectivity_Status(String BT_Connectivity_Status) {
        this.BT_Connectivity_Status = BT_Connectivity_Status;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryName_headUnit() {
        return CategoryName_headUnit;
    }

    public void setCategoryName_headUnit(String categoryName_headUnit) {
        CategoryName_headUnit = categoryName_headUnit;
    }

    public String getEnd_time_for_BT_connectivity() {
        return End_time_for_BT_connectivity;
    }

    public void setEnd_time_for_BT_connectivity(String end_time_for_BT_connectivity) {
        End_time_for_BT_connectivity = end_time_for_BT_connectivity;
    }

    public String getEnd_time_on_Channel() {
        return End_time_on_Channel;
    }

    public void setEnd_time_on_Channel(String end_time_on_Channel) {
        End_time_on_Channel = end_time_on_Channel;
    }

    public String getFrequency_of_channel() {
        return Frequency_of_channel;
    }

    public void setFrequency_of_channel(String frequency_of_channel) {
        Frequency_of_channel = frequency_of_channel;
    }

    public String getKeyType() {
        return KeyType;
    }

    public void setKeyType(String keyType) {
        KeyType = keyType;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
    }

    public String getStart_time_for_BT_connectivity() {
        return Start_time_for_BT_connectivity;
    }

    public void setStart_time_for_BT_connectivity(String start_time_for_BT_connectivity) {
        Start_time_for_BT_connectivity = start_time_for_BT_connectivity;
    }

    public String getStart_time_on_Channel() {
        return Start_time_on_Channel;
    }

    public void setStart_time_on_Channel(String start_time_on_Channel) {
        Start_time_on_Channel = start_time_on_Channel;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getTime_BT_is_connected() {
        return Time_BT_is_connected;
    }

    public void setTime_BT_is_connected(String time_BT_is_connected) {
        Time_BT_is_connected = time_BT_is_connected;
    }

    public String getTime_Spent_on_the_Channel() {
        return Time_Spent_on_the_Channel;
    }

    public void setTime_Spent_on_the_Channel(String time_Spent_on_the_Channel) {
        Time_Spent_on_the_Channel = time_Spent_on_the_Channel;
    }

    public String getDirection_headUnit() {
        return direction_headUnit;
    }

    public void setDirection_headUnit(String direction_headUnit) {
        this.direction_headUnit = direction_headUnit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProgramme_identification() {
        return programme_identification;
    }

    public void setProgramme_identification(String programme_identification) {
        this.programme_identification = programme_identification;
    }

    public String getProgramme_type() {
        return programme_type;
    }

    public void setProgramme_type(String programme_type) {
        this.programme_type = programme_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_headUnit() {
        return source_headUnit;
    }

    public void setSource_headUnit(String source_headUnit) {
        this.source_headUnit = source_headUnit;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getWaveband() {
        return waveband;
    }

    public void setWaveband(String waveband) {
        this.waveband = waveband;
    }

    String Gps_status;
    String direction;
    String engine_temp;
    String ignition;
    String lat;
    String lon;
    String lights;
    String odometer;
    String speedOfVehicle;
    String tire_pressure;
    String track_time;
    String ABS_Warning_Request;
    String Battery_Warning;
    String Boot_Door_Warning;
    String Distance_Totalizer;
    String Driver_Door_Locked;
    String Engine_Coolant_Temperature;
    String Engine_RPM;
    String Front_Left_Door_Warning;
    String Front_Right_Door_Warning;
    String Fuel_Low;
    String High_Beam;
    String Longitudinal_Acceleration;
    String Low_Beam;
    String Mean_Effective_Torque;
    String Parking_Brake;
    String Raw_Sensor;
    String Rear_Left_Door_Warning;
    String Rear_Right_Door_Warning;
    String Seat_Belt_2nd_Row_Center_Status;
    String Seat_Belt_2nd_Row_Left_Status;
    String Seat_Belt_2nd_Row_Right_Status;
    String Seat_Belt_Driver_Reminder;
    String Seat_Belt_Front_Passenger_Reminder;
    String Speed_Wheel_Front_Left;
    String Speed_Wheel_Front_Right;
    String Speed_Wheel_Rear_Left;
    String Speed_Wheel_Rear_Right;
    String Steering_Wheel_Angle;
    String Steering_Wheel_Angle_Offset;
    String Steering_Wheel_Rotation_Speed;
    String Transversal_Acceleration;
    String VehicleBatteryVoltage;
    String Vehicle_Speed;
    String Warning_Lights;
    String internalbattvolts;
    String Action;
    String AppName;
    String AppName_headUnit;
    String BT_Connectivity_Status;
    String CategoryName;
    String CategoryName_headUnit;
    String End_time_for_BT_connectivity;
    String End_time_on_Channel;
    String Frequency_of_channel;
    String KeyType;
    String ScreenName;
    String Start_time_for_BT_connectivity;
    String Start_time_on_Channel;
    String State;
    String Time_BT_is_connected;
    String Time_Spent_on_the_Channel;
    String direction_headUnit;
    String key;
    String programme_identification;
    String programme_type;
    String source;
    String source_headUnit;
    String stationName;
    String waveband;



}
