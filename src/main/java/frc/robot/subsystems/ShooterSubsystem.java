package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class ShooterSubsystem extends SubsystemBase {

    public final WPI_TalonSRX shooterTalonSRXRightTalonSRX= new WPI_TalonSRX(Constants.SHOOTER_MOTOR_RIGHT);
    public final WPI_TalonSRX shooterTalonSRX2LefTalonSRX = new WPI_TalonSRX(Constants.SHOOTER_MOTOR_LEFT);
    public final WPI_TalonSRX pusherTalonSRX2 = new WPI_TalonSRX(Constants.PUSHER_MOTOR);

    public void startShooter() {
        double currentTime = Timer.getFPGATimestamp();
        while (Timer.getFPGATimestamp()<= currentTime+0.8) {
            shooterTalonSRXRightTalonSRX.set(1);
            shooterTalonSRX2LefTalonSRX.set(-1);
        }
        shooterTalonSRXRightTalonSRX.set(0);
        shooterTalonSRX2LefTalonSRX.set(0);
    }   
    
    public void startPusher(){
        pusherTalonSRX2.set(1);
    }

    public void stopShooter() {
        shooterTalonSRXRightTalonSRX.set(0);
        shooterTalonSRX2LefTalonSRX.set(0);
    }
    public void stopPusher(){
        pusherTalonSRX2.set(0);
    }
  }