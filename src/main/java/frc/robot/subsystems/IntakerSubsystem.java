package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class IntakerSubsystem extends SubsystemBase {
    public final WPI_TalonSRX intakerTalonSRX = new WPI_TalonSRX(Constants.INTAKER_MOTOR);

    public void startIntaker() {
        intakerTalonSRX.set(1);
    }
  
    /**
     * Releases the hatch.
     */
    public void stopIntaker() {
        intakerTalonSRX.set(0);
    }
  }