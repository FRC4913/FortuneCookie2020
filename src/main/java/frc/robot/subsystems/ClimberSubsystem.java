package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  private final SpeedController climberTalonSRX = new WPI_TalonSRX(Constants.CLIMBER_MOTOR);

  public void climbUp() {
    climberTalonSRX.set(1);
  }

  public void climbDown() {
    climberTalonSRX.set(-1);
  }

  public void climbStop() {
    climberTalonSRX.stopMotor();
  }
}