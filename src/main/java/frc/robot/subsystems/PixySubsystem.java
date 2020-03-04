package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.pseudoresonance.pixy2api.*;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;

public class PixySubsystem extends SubsystemBase {
    private Pixy2 pixycam;
    boolean ifInitalized = false;

    public PixySubsystem() {

    }

    public int init(){
        pixycam = Pixy2.createInstance(Pixy2.LinkType.SPI);
        return 1;
    }

    public void periodicUpdateLog(){
        if(!ifInitalized){
            int state = pixycam.init(1);
            ifInitalized = state >= 0;
        }
        SmartDashboard.putBoolean( "Camera", ifInitalized);

        pixycam.getCCC().getBlocks(false, 255, 255);
        ArrayList<Block> blocks = pixycam.getCCC().getBlocks();
        if(blocks.size() > 0){
            double xcoord = blocks.get(0).getX();
            double ycoord = blocks.get(0).getY();
            String data = blocks.get(0).toString();
            SmartDashboard.putBoolean("present", true);
            SmartDashboard.putNumber("Xcoord", xcoord);
            SmartDashboard.putNumber("Ycoord", ycoord);
            SmartDashboard.putString("Data", data);
        }else{
            SmartDashboard.putBoolean("present", false);
        }

        SmartDashboard.putNumber("size", blocks.size());
    }
}