package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//for network table

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class RasberryPiConnector extends SubsystemBase{
    NetworkTableInstance networkInstance = NetworkTableInstance.getDefault();

    public RasberryPiConnector() {
        //table
        NetworkTable RasberryPiTable = networkInstance.getTable("RasberryPidatatable");
        //entry
        NetworkTableEntry xEntry = RasberryPiTable.getEntry("X");

        SmartDashboard.putString("datatable test variable: " ,  xEntry.getString("Error"));
    }

}

