/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class DriveConstants {
        public static final int FRONT_LEFT_MOTOR_ID = 6;
        public static final int FRONT_RIGHT_MOTOR_ID = 7;
        public static final int REAR_LEFT_MOTOR_ID = 2;
        public static final int REAR_RIGHT_MOTOR_ID = 4;
    }

    public static final class OIConstants {
        public static final int XBOX_CONTROLLER = 0;
        public static final int XBOX_CONTROLLER2 = 1;
    }

    public static final I2C.Port I2C_PORT = I2C.Port.kOnboard;
    public static final int LOADER_MOTOR = 1;
    public static final int INTAKER_MOTOR = 5;
    public static final int SHOOTER_MOTOR_LEFT = 3;
    public static final int SHOOTER_MOTOR_RIGHT = 12;
    public static final int COLOR_ROTATOR_MOTOR = 10;
    public static final int CLIMBER_MOTOR = 11;
}
