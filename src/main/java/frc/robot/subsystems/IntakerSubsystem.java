package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class IntakerSubsystem extends SubsystemBase {
    private final WPI_TalonSRX intakerTalonSRX = new WPI_TalonSRX(Constants.INTAKER_MOTOR);
    private final WPI_TalonSRX loaderTalonSRX = new WPI_TalonSRX(Constants.LOADER_MOTOR);

    public void startIntaker() {
        intakerTalonSRX.set(1);
    }

    public void stopIntaker() {
        intakerTalonSRX.set(0);
    }

    /**
     * Turn on intaker mechanism. Also turns on the loader motor.
     */
    public void intake() {
        intakerTalonSRX.set(1);
        loaderTalonSRX.set(1);
    }

    /**
     * Stop the intaker mechanism.
     */
    public void stop() {
        intakerTalonSRX.set(0);
        loaderTalonSRX.set(0);
    }

}
