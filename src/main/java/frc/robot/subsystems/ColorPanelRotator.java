package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ColorPanelRotator extends SubsystemBase {
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.I2C_PORT);
  private final SpeedController colorRotatorMotor = new WPI_VictorSPX(Constants.COLOR_ROTATOR_MOTOR);

  ColorMatch colorMatcher = new ColorMatch();

  Color kBlueTarget = ColorMatch.makeColor(0.122, 0.426, 0.451);
  Color kRedTarget = ColorMatch.makeColor(0.523, 0.345, 0.132);
  Color kYellowTarget = ColorMatch.makeColor(0.318, 0.560, 0.123);
  Color kGreenTarget = ColorMatch.makeColor(0.167, 0.578, 0.256);

  // variables for rotateByNumber
  private final int NUM_ROTATIONS = 4;
  private final double MOTOR_SPEED = 0.4;

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
        case 'b':
          rotateToColor("Blue");
          break;
        case 'G':
        case 'g':
          // Green case code
          rotateToColor("Green");
          break;
        case 'R':
        case 'r':
          // Red case code
          rotateToColor("Red");
          break;
        case 'Y':
        case 'y':
          // Yellow case code
          rotateToColor("Yellow");
          break;
        default:
          // This is corrupt data
          break;
      }
    } else {
      // Code for no data received
      colorRotatorMotor.stopMotor();
    }
  }

  public void rotateToColor(String targetColor) {
    if (getColor() == targetColor) {
      colorRotatorMotor.stopMotor();
    } else
      colorRotatorMotor.set(MOTOR_SPEED);
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
      colorRotatorMotor.set(leftTriggerPressure);
    } else if (rightTriggerPressure != 0) {
      colorRotatorMotor.set(-rightTriggerPressure);
    } else {
      colorRotatorMotor.stopMotor();
    }
  }

  public void startNum() {
    startingColor = "";
  }

  // variables for numOfRotation
  private int numOfRots = 0;
  private String startingColor = "";
  private boolean isAtStartingColor = true;

  public void rotateByNumber() {
    if (startingColor.length() > 0) {
      if (numOfRots < NUM_ROTATIONS) {
        if (startingColor == getColor()) {
          colorRotatorMotor.set(MOTOR_SPEED);
          if (!isAtStartingColor) {
            numOfRots++;
            isAtStartingColor = true;
          }
        } else {
          rotateToColor(startingColor);
          isAtStartingColor = false;
        }
      } else {
        colorRotatorMotor.stopMotor();
      }
    } else {
      startingColor = getColor();
      numOfRots = 0;
    }
    SmartDashboard.putNumber("number of rotations", numOfRots);
  }

  // Not being used currently
  public void rotateOnce() {
    int currentNumRotations = 0;
    String startingColor = getColor();

    // first move off the current color
    moveOffCurrentColor();

    while (currentNumRotations < 1) {
      if (getColor() == startingColor) {
        currentNumRotations++;
        colorRotatorMotor.stopMotor();
        moveOffCurrentColor();
      } else
        colorRotatorMotor.set(MOTOR_SPEED);
    }
    colorRotatorMotor.stopMotor();
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
      colorRotatorMotor.set(MOTOR_SPEED);
    } while (currentColor == startingColor);
    colorRotatorMotor.stopMotor();
  }

  public void stop() {
    colorRotatorMotor.stopMotor();
  }

  public void periodic() {
  }
}