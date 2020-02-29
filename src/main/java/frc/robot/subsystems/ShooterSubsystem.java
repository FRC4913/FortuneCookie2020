package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class ShooterSubsystem extends SubsystemBase {
    public final WPI_TalonSRX shooterLeftTalonSRX = new WPI_TalonSRX(Constants.SHOOTER_LEFT_MOTOR);
    public final WPI_TalonSRX shooterRightTalonSRX = new WPI_TalonSRX(Constants.SHOOTER_RIGHT_MOTOR);

    public void startShooter() {
        shooterLeftTalonSRX.set(1);
        shooterRightTalonSRX.set(-1);
    }

    /**
     * Releases the hatch.
     */
    public void stopShooter() {
        shooterLeftTalonSRX.set(0);
        shooterRightTalonSRX.set(-1);
    }
  }