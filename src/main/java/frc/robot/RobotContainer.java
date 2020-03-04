/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.cameraserver.CameraServer;


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
  private final IntakerSubsystem intakerSubsystem = new IntakerSubsystem();
  private final LoaderSubsystem loaderSubsystem = new LoaderSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();


  XboxController xboxController = new XboxController(OIConstants.XBOX_CONTROLLER);
  XboxController xboxController2 = new XboxController(OIConstants.XBOX_CONTROLLER2);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    colorPanelRotator.setDefaultCommand(new RunCommand(
        () -> colorPanelRotator.manualRotation(xboxController2.getRawAxis(2), xboxController2.getRawAxis(3)),
        colorPanelRotator));

    driveSubsystem
        .setDefaultCommand(new RunCommand(
            () -> driveSubsystem.drive(xboxController.getY(GenericHID.Hand.kLeft),
                xboxController.getY(GenericHID.Hand.kRight), xboxController.getX(GenericHID.Hand.kRight)),
            driveSubsystem));

    //live-camera (microsoft HD-3000)
    CameraServer.getInstance().startAutomaticCapture(0);

  }
  
  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(xboxController2, Button.kBumperLeft.value)
        .whileHeld(new InstantCommand(colorPanelRotator::rotateToGameColor, colorPanelRotator))
        .whenReleased(new InstantCommand(colorPanelRotator::stop, colorPanelRotator));
    new JoystickButton(xboxController, Button.kBack.value).whenPressed(() -> driveSubsystem.convertToTank());
    new JoystickButton(xboxController, Button.kStart.value).whenPressed(() -> driveSubsystem.convertToArcade());
    new JoystickButton(xboxController2, Button.kB.value)
        .whenPressed(new InstantCommand(intakerSubsystem::startIntaker, intakerSubsystem))
        .whenReleased(new InstantCommand(intakerSubsystem::stopIntaker, intakerSubsystem));

    //loader
    new JoystickButton(xboxController, Button.kY.value)
        .whileHeld(new SequentialCommandGroup(new InstantCommand(shooterSubsystem::startShooter, shooterSubsystem),
            new WaitCommand(1), new InstantCommand(loaderSubsystem::startLoaderUp, loaderSubsystem)))
        .whenReleased(new SequentialCommandGroup(new InstantCommand(shooterSubsystem::stopShooter, shooterSubsystem),
            new InstantCommand(loaderSubsystem::stopLoader, loaderSubsystem)));
    new JoystickButton(xboxController, Button.kA.value)
        .whileHeld(new SequentialCommandGroup(new InstantCommand(shooterSubsystem::startShooter, shooterSubsystem),
            new WaitCommand(1), new InstantCommand(loaderSubsystem::startLoaderDown, loaderSubsystem)))
        .whenReleased(new SequentialCommandGroup(new InstantCommand(shooterSubsystem::stopShooter, shooterSubsystem),
            new InstantCommand(loaderSubsystem::stopLoader, loaderSubsystem)));
    
    

    new JoystickButton(xboxController2, Button.kBumperRight.value)
        .whileHeld(new InstantCommand(colorPanelRotator::rotateByNumber, colorPanelRotator))
        .whenReleased(new InstantCommand(colorPanelRotator::startNum, colorPanelRotator));

    
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