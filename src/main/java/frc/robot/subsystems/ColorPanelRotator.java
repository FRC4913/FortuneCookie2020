package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ColorPanelRotator extends SubsystemBase {
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.I2C_PORT);
  private final Spark colorPanelSpark = new Spark(Constants.COLOR_PANEL_ROTATOR_MOTOR_ID);

  public ColorPanelRotator() {
  }

  public void rotate() {
    colorPanelSpark.setSpeed(0.5);
  }

  public void stop() {
    colorPanelSpark.setSpeed(0);
  }

  public void periodic() {
  }
}