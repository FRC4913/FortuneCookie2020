package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ShooterSubsystem extends SubsystemBase {
    public final WPI_TalonSRX shooterTalonSRX = new WPI_TalonSRX(Constants.SHOOTER_MOTOR);

    public void startShooter() {
        shooterTalonSRX.set(1);
    }
  
    /**
     * Releases the hatch.
     */
    public void stopShooter() {
        shooterTalonSRX.set(0);
    }
  }