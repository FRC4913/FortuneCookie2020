/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DigitalSource;
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
		public static final DigitalSource[] kLeftEncoderPorts = null;
		public static final DigitalSource kLeftEncoderReversed = null;
		public static final DigitalSource kRightEncoderReversed = null;
		public static final DigitalSource[] kRightEncoderPorts = null;
		public static final double kEncoderDistancePerPulse = (.0254*7.25*Math.PI)/2048;
		public static final boolean kGyroReversed = false;
    }

    public static final class OIConstants {
        public static final int XBOX_CONTROLLER = 0;
    }

    public static final int COLOR_PANEL_ROTATOR_MOTOR_ID = 0;
    public static final I2C.Port I2C_PORT = I2C.Port.kOnboard;
    public static final int LOADER_MOTOR = 8; //Temporary placeholder
    public static final int PUSHER_MOTOR = 9; //Temp
    public static final int INTAKER_MOTOR = 5;
<<<<<<< Updated upstream
    public static final int SHOOTER_MOTOR_RIGHT = 3;
    public static final int SHOOTER_MOTOR_LEFT = 1;

=======
    public static final int SHOOTER_LEFT_MOTOR = 3;
    public static final int SHOOTER_RIGHT_MOTOR = 4; //placeholder
    public static final int PUSHER_MOTOR = 6; //placeholder
    public static final int CLIMBER_MOTOR = 7; //placeholder
>>>>>>> Stashed changes
}
