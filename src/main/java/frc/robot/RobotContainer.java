/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//import org.graalvm.compiler.core.common.type.ArithmeticOpTable.BinaryOp.Xor;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.ColorPanelRotator;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakerSubsystem;
import frc.robot.subsystems.LoaderSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final ColorPanelRotator colorPanelRotator = new ColorPanelRotator();
  private final IntakerSubsystem intakeSub = new IntakerSubsystem();
  private final LoaderSubsystem loadSub = new LoaderSubsystem();
  private final ShooterSubsystem shootSub = new ShooterSubsystem();

  XboxController xboxController = new XboxController(OIConstants.XBOX_CONTROLLER);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    //colorPanelRotator.setDefaultCommand(new RunCommand(()->colorPanelRotator.forward(xboxController.getRawAxis(2)),colorPanelRotator));
    //colorPanelRotator
      //.setCommand(new RunCommand(()->colorPanelRotator.backward(xboxController.getRawAxis(3)),colorPanelRotator))
      //.(new RunCommand(()->colorPanelRotator.forward(xboxController.getRawAxis(2)),colorPanelRotator));
    colorPanelRotator
      .setDefaultCommand(new RunCommand(()->colorPanelRotator.manualRotation(xboxController.getRawAxis(2), 
        xboxController.getRawAxis(3)), colorPanelRotator));



    driveSubsystem
        .setDefaultCommand(new RunCommand(() -> driveSubsystem.arcadeDrive(xboxController.getY(GenericHID.Hand.kLeft),
            xboxController.getX(GenericHID.Hand.kRight)), driveSubsystem));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(xboxController, Button.kB.value)
        .whileHeld(new InstantCommand(colorPanelRotator::rotateToGameColor, colorPanelRotator))
        .whenReleased(new InstantCommand(colorPanelRotator::stop, colorPanelRotator));

    new JoystickButton(xboxController, Button.kA.value)
        .whenPressed(new InstantCommand(colorPanelRotator::numOfRotation, colorPanelRotator));
        //.whileHeld(new InstantCommand(colorPanelRotator::tester, colorPanelRotator))
        //.whenReleased(new InstantCommand(colorPanelRotator::stop, colorPanelRotator));

    new JoystickButton(xboxController, Button.kBumperLeft.value)
        .whileHeld(new InstantCommand(intakeSub::startIntaker, intakeSub))
        .whenReleased(new InstantCommand(intakeSub::stopIntaker, intakeSub));

    new JoystickButton(xboxController, Button.kY.value)
        .whileHeld(new SequentialCommandGroup(
          new InstantCommand(shootSub::startShooter, shootSub),
          new WaitCommand(2),
          new InstantCommand(loadSub::startLoader, loadSub)))
        .whenReleased(new SequentialCommandGroup(
          new InstantCommand(shootSub::stopShooter, shootSub),
          new InstantCommand(loadSub::stopLoader, loadSub)));
      
     // colorPanelRotator
       // .getCurrentCommand(new InstantCommand(() -> colorPanelRotator.forward(xboxController.getTriggerAxis(GenericHID.Hand.kLeft))))
        //.getCurrentCommand(new InstantCommand(() -> colorPanelRotator.backward(xboxController.getTriggerAxis(GenericHID.Hand.kRight))));

  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
