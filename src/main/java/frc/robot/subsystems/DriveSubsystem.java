/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

  private final SpeedControllerGroup leftControllerGroup = new SpeedControllerGroup(
      new WPI_TalonSRX(DriveConstants.FRONT_LEFT_MOTOR_ID), new WPI_TalonSRX(DriveConstants.REAR_LEFT_MOTOR_ID));

  private final SpeedControllerGroup rightControllerGroup = new SpeedControllerGroup(
      new WPI_TalonSRX(DriveConstants.FRONT_RIGHT_MOTOR_ID), new WPI_TalonSRX(DriveConstants.REAR_RIGHT_MOTOR_ID));

  private final DifferentialDrive drive = new DifferentialDrive(leftControllerGroup, rightControllerGroup);

  private boolean isArcade = true;

  public DriveSubsystem() {
    SmartDashboard.putString("mode", "arcade");
  }

  public void drive(double leftY, double rightY, double rightX) {

    if (isArcade) {
      drive.arcadeDrive(-leftY, rightX);
    } else {
      drive.tankDrive(-leftY, -rightY);
    }
  }

  public void convertToArcade() {
    isArcade = true;
    SmartDashboard.putString("mode", "arcade");
  }

  public void convertToTank() {
    isArcade = false;
    SmartDashboard.putString("mode", "tank");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
