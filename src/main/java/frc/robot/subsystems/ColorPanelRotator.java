package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotContainer;

public class ColorPanelRotator extends SubsystemBase {
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.I2C_PORT);
  private final Spark colorPanelSpark = new Spark(Constants.COLOR_PANEL_ROTATOR_MOTOR_ID);
  private int numOfRots=0;
  private String startingColor;
  private boolean isAtStartingColor = true;

  public double leftTriggerPressure;
  public double rightTriggerPressure;

  public ColorPanelRotator() {
  }

  public void rotateToGameColor(){
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
    } else{
      // Code for no data received
      colorPanelSpark.setSpeed(0);
    }
  }

  public void rotateToColor(String targetColor) {
      colorPanelSpark.setSpeed(0.5);
      String colorString=getColor(); 
      if(colorString == targetColor){
        colorPanelSpark.setSpeed(0);
      }
      else {
      colorPanelSpark.setSpeed(0.5);
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

  /*
  public void tester() {
    for(int i=0;i<5;i++){
      while("Blue".compareTo(getColor())==0){
        colorPanelSpark.setSpeed(.5);
      }
      rotateToColor("Blue");
    }
  }
  */

  /*public void forward(double leftTriggerPressure) {
    colorPanelSpark.setSpeed(leftTriggerPressure);
  }

  public void backward(double rightTriggerPressure) {
    colorPanelSpark.setSpeed(-rightTriggerPressure);
  }*/

  public void manualRotation(double leftTriggerPressure, double rightTriggerPressure) {
    if(leftTriggerPressure!=0){
      colorPanelSpark.setSpeed(leftTriggerPressure);
    }
    else if (rightTriggerPressure!=0){
      colorPanelSpark.setSpeed(-rightTriggerPressure);
    }
    else{
      colorPanelSpark.setSpeed(0);
    }
    SmartDashboard.putNumber("left", leftTriggerPressure);
    SmartDashboard.putNumber("right", rightTriggerPressure);
    SmartDashboard.updateValues();
  }

  public void numOfRotation() {
    startingColor = getColor();
    
    if(isAtStartingColor==true) {
      colorPanelSpark.setSpeed(0.5);
      if(getColor()!=startingColor) {
        isAtStartingColor = false;
      }
    } else {
      rotateToColor(startingColor);
      numOfRots++;
      if(numOfRots!=4)
      {
        isAtStartingColor=true;
        colorPanelSpark.setSpeed(0.5);
      }
    }
  }
  


  public void stop() {
    colorPanelSpark.setSpeed(0);
  }

  public void startTest(){
    colorPanelSpark.setSpeed(1);
  }

  public void periodic() {
  }
}