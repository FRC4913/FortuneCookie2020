package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ClimbingSubsystem extends SubsystemBase {
    public final WPI_TalonSRX climberTalonSRX = new WPI_TalonSRX(Constants.CLIMBER_MOTOR);

    public void climbingUp() {
        climberTalonSRX.set(1);
    }

    public void climbingDown() {
        climberTalonSRX.set(-1);
    }

    public void climbingStop() {
        climberTalonSRX.set(0);
    }
  }