package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ColorPanelRotator extends SubsystemBase {
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.I2C_PORT);
  private final Spark colorPanelSpark = new Spark(Constants.COLOR_PANEL_ROTATOR_MOTOR_ID);
  private int numOfRots=0;
  private String startingColor;

  public ColorPanelRotator() {
  }

  public void rotateToGameColor(){
    String colorChar = DriverStation.getInstance().getGameSpecificMessage();
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
      }}
  }

  public void rotateToColor(String targetColor) {
      colorPanelSpark.setSpeed(0.5);
      String colorString=getColor();
      if(colorString == targetColor){
        colorPanelSpark.setSpeed(0);
      }
      else {
      // Code for no data received yet
      colorPanelSpark.setSpeed(0);
    }
  }

  public String getColor(){
    ColorMatch colorMatcher = new ColorMatch();

      Color kBlueTarget = ColorMatch.makeColor(0.122, 0.426, 0.451);
      Color kRedTarget = ColorMatch.makeColor(0.523, 0.345, 0.132);
      Color kYellowTarget = ColorMatch.makeColor(0.318, 0.560, 0.123);
      Color kGreenTarget = ColorMatch.makeColor(0.167, 0.578, 0.256);

      colorMatcher.addColorMatch(kBlueTarget);
      colorMatcher.addColorMatch(kGreenTarget);
      colorMatcher.addColorMatch(kRedTarget);
      colorMatcher.addColorMatch(kYellowTarget);

      Color detectedColor = colorSensor.getColor();
      String colorString;
      ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

      if (match.color == kBlueTarget) {
        colorString = "Blue";
      } else if (match.color == kRedTarget) {
        colorString = "Red";
      } else if (match.color == kGreenTarget) {
        colorString = "Green";
      } else if (match.color == kYellowTarget) {
        colorString = "Yellow";
      } else {
        colorString = "Unknown";
      }
      SmartDashboard.updateValues();
      SmartDashboard.putString("DetectedColor", colorString);
      SmartDashboard.putNumber("red", detectedColor.red);
      SmartDashboard.putNumber("green", detectedColor.green);
      SmartDashboard.putNumber("blue", detectedColor.blue);
      SmartDashboard.putNumber("confidence", match.confidence);
      return colorString;
  }

  public void rotate(int numRotations) {
    if(getColor().equals("Blue")){
      rotateToColor("Red");
    }
    }
  }

  public void stop() {
    colorPanelSpark.setSpeed(0);
  }

  public void periodic() {
  }
}