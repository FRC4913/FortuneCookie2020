package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ColorPanelRotator extends SubsystemBase {
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.I2C_PORT);
  private final Spark colorPanelSpark = new Spark(Constants.COLOR_PANEL_ROTATOR_MOTOR_ID);

  private final int NUM_ROTATIONS = 4;
  private final double MOTOR_SPEED = 0.4;

  private final ColorMatch colorMatcher = new ColorMatch();
  private final Color kBlueTarget = ColorMatch.makeColor(0.123, 0.427, 0.450);
  private final Color kRedTarget = ColorMatch.makeColor(0.521, 0.346, 0.132);
  private final Color kYellowTarget = ColorMatch.makeColor(0.317, 0.559, 0.124);
  private final Color kGreenTarget = ColorMatch.makeColor(0.168, 0.577, 0.254);

  public double leftTriggerPressure;
  public double rightTriggerPressure;

  public ColorPanelRotator() {
    colorMatcher.addColorMatch(kBlueTarget);
    colorMatcher.addColorMatch(kGreenTarget);
    colorMatcher.addColorMatch(kRedTarget);
    colorMatcher.addColorMatch(kYellowTarget);
  }

  public void rotateToGameColor() {
    String colorChar = DriverStation.getInstance().getGameSpecificMessage();
    SmartDashboard.putString("colorchar", colorChar);
    if (colorChar.length() > 0) {
      switch (DriverStation.getInstance().getGameSpecificMessage().charAt(0)) {
      case 'B':
        rotateToColor("Blue");
        break;
      case 'G':
        // Green case code
        rotateToColor("Green");
        break;
      case 'R':
        // Red case code
        rotateToColor("Red");
        break;
      case 'Y':
        // Yellow case code
        rotateToColor("Yellow");
        break;
      default:
        // This is corrupt data
        break;
      }
    } else {
      // Code for no data received
      colorPanelSpark.setSpeed(0);
    }
  }

  public void rotateToColor(String targetColor) {
    colorPanelSpark.setSpeed(0.5);
    String colorString = getColor();
    if (colorString == targetColor) {
      colorPanelSpark.setSpeed(0);
    }
  }

  public String getColor() {
    Color detectedColor = colorSensor.getColor();
    String colorString = "Unknown";
    ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    }

    SmartDashboard.updateValues();
    SmartDashboard.putString("DetectedColor", colorString);
    SmartDashboard.putNumber("red", detectedColor.red);
    SmartDashboard.putNumber("green", detectedColor.green);
    SmartDashboard.putNumber("blue", detectedColor.blue);
    SmartDashboard.putNumber("confidence", match.confidence);
    return colorString;
  }

  public void manualRotation(double leftTriggerPressure, double rightTriggerPressure) {
    if (leftTriggerPressure != 0) {
      colorPanelSpark.setSpeed(leftTriggerPressure);
    } else if (rightTriggerPressure != 0) {
      colorPanelSpark.setSpeed(-rightTriggerPressure);
    } else {
      colorPanelSpark.setSpeed(0);
    }
    getColor();
    SmartDashboard.putNumber("left", leftTriggerPressure);
    SmartDashboard.putNumber("right", rightTriggerPressure);
    SmartDashboard.updateValues();
  }

  public void rotateByNumber() {
    int currentNumRotations = 0;
    String startingColor = getColor();

    // first move off the current color
    moveOffCurrentColor();

    while (currentNumRotations < NUM_ROTATIONS) {
      colorPanelSpark.setSpeed(MOTOR_SPEED);
      if (getColor() == startingColor) {
        currentNumRotations++;
        colorPanelSpark.setSpeed(0);
        moveOffCurrentColor();
      }
    }
    colorPanelSpark.setSpeed(0);
  }

  public void stop() {
    colorPanelSpark.setSpeed(0);
  }

  public void startTest() {
    colorPanelSpark.setSpeed(1);
  }

  public void periodic() {
  }

  /**
   * Move off the current color. Turn on the motor until the current color no
   * longer matches the starting color, then stop the motor.
   */
  private void moveOffCurrentColor() {
    String startingColor = getColor();
    String currentColor;
    do {
      currentColor = getColor();
      colorPanelSpark.setSpeed(MOTOR_SPEED);
    } while (currentColor == startingColor);
    colorPanelSpark.setSpeed(0);
  }

}