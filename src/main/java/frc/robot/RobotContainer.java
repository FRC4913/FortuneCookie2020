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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();

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

    SmartDashboard.putData("Intake", new InstantCommand(intakerSubsystem::intake, intakerSubsystem));
    // live-camera (microsoft HD-3000)
    CameraServer.getInstance().startAutomaticCapture(0);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
      /**
       * 
       * Button Mappings:
       * 
       * - Xbox 1:
       *        kBumperLeft: Shooter
       *        kBumperRight: Intaker
       *        kBack: convert Drive to tank mode
       *        kStart: convert drive to arcade mode
       *        kY: loader up
       *        kA: loader down
       *        left, right axes: drive
       * 
       * - Xbox 2:
       *        leftTrigger: manual rotation -
       *        rightTrigger: manual rotation +
       *        kBumperLeft: color rotation
       *        kBumperRight: number rotation
       *        kY: climb up
       *        kA: climb down
       */

      // xbox controller2 rotate to game color
      new JoystickButton(xboxController2, Button.kBumperLeft.value)
              .whileHeld(new InstantCommand(colorPanelRotator::rotateToGameColor, colorPanelRotator))
              .whenReleased(new InstantCommand(colorPanelRotator::stop, colorPanelRotator));

      // xbox controller1 convert to tank/arcade
      new JoystickButton(xboxController, Button.kBack.value).whenPressed(() -> driveSubsystem.convertToTank());
      new JoystickButton(xboxController, Button.kStart.value).whenPressed(() -> driveSubsystem.convertToArcade());

      // xbox controller1 intaker
      new JoystickButton(xboxController, Button.kBumperRight.value)
              .whenPressed(new InstantCommand(intakerSubsystem::intake, intakerSubsystem))
              .whenReleased(new InstantCommand(intakerSubsystem::stop, intakerSubsystem));

      // xbox controller1 loader up
      new JoystickButton(xboxController, Button.kY.value)
                      .whenPressed(new InstantCommand(loaderSubsystem::loaderUp, loaderSubsystem))
                      .whenReleased(new InstantCommand(loaderSubsystem::stopLoader, loaderSubsystem));

      // xbox controller1 loader down
      new JoystickButton(xboxController, Button.kA.value)
                      .whenPressed(new InstantCommand(loaderSubsystem::loaderDown, loaderSubsystem))
                      .whenReleased(new InstantCommand(loaderSubsystem::stopLoader, loaderSubsystem));

      // xbox controller1 shooter
      new JoystickButton(xboxController, Button.kBumperLeft.value)
              .whileHeld(
                      new SequentialCommandGroup(new InstantCommand(shooterSubsystem::startShooter, shooterSubsystem),
                              new WaitCommand(3), new InstantCommand(loaderSubsystem::loaderUp, loaderSubsystem)))
              .whenReleased(
                      new SequentialCommandGroup(new InstantCommand(shooterSubsystem::stopShooter, shooterSubsystem),
                              new InstantCommand(loaderSubsystem::stopLoader, loaderSubsystem)));

      // xbox controller2 color panel rotate by number
      new JoystickButton(xboxController2, Button.kBumperRight.value)
              .whileHeld(new InstantCommand(colorPanelRotator::rotateByNumber, colorPanelRotator))
              .whenReleased(new InstantCommand(colorPanelRotator::startNum, colorPanelRotator));

      // xbox controller2 climber
      new JoystickButton(xboxController2, Button.kY.value)
              .whileHeld(new InstantCommand(climberSubsystem::climbUp, climberSubsystem))
              .whenReleased(new InstantCommand(climberSubsystem::climbStop));
      new JoystickButton(xboxController2, Button.kA.value)
              .whileHeld(new InstantCommand(climberSubsystem::climbDown, climberSubsystem))
              .whenReleased(new InstantCommand(climberSubsystem::climbStop));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new SequentialCommandGroup(
                new InstantCommand(shooterSubsystem::startShooter, shooterSubsystem),
                new WaitCommand(10),
                new InstantCommand(shooterSubsystem::stopShooter, shooterSubsystem),
                new InstantCommand(() -> driveSubsystem.drive(-0.5, 0, 0), driveSubsystem),
                new WaitCommand(3),
                new InstantCommand(driveSubsystem::stopDrive, driveSubsystem)
            );
  }
}