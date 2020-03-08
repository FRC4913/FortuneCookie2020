package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
    private final SpeedController shooterLeftController = new WPI_VictorSPX(Constants.SHOOTER_MOTOR_LEFT);
    private final SpeedController shooterRightController = new WPI_VictorSPX(Constants.SHOOTER_MOTOR_RIGHT);

    public void startShooter() {
        shooterLeftController.set(1);
        shooterRightController.set(-1);
    }

    public void stopShooter() {
        shooterLeftController.stopMotor();
        shooterRightController.stopMotor();
    }
}