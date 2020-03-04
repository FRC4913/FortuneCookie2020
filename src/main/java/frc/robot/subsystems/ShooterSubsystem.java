package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ShooterSubsystem extends SubsystemBase {
    public final WPI_TalonSRX shooterTalonSRX = new WPI_TalonSRX(Constants.SHOOTER_MOTOR);

    private final Spark leftShooterSpark = new Spark(Constants.LEFT_SHOOTER); 
    private final Spark rightShooterSpark = new Spark(Constants.RIGHT_SHOOTER);
    // public final SpeedControllerGroup 

    public void startShooter() {
        shooterTalonSRX.set(1);
    }

    public void startLeftShooter(){
        leftShooterSpark.setSpeed(1);
    }

    public void rightLeftShooter(){
        rightShooterSpark.setSpeed(1);
    }
  
    /**
     * Releases the hatch.
     */
    public void stopShooter() {
        shooterTalonSRX.set(0);
    }

    
    public void stopLeftShooter(){
        leftShooterSpark.setSpeed(0);
    }

    public void stopRightShooter(){
        rightShooterSpark.setSpeed(0);
    }
  }