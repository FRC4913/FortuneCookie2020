package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class LoaderSubsystem extends SubsystemBase {
    public final WPI_TalonSRX loaderTalonSRX = new WPI_TalonSRX(Constants.LOADER_MOTOR);

    public void startLoaderUp() {
        loaderTalonSRX.set(1);
    }

    public void startLoaderDown() {
        loaderTalonSRX.set(-1);
    }
  
    /**
     * Releases the hatch.
     */
    public void stopLoader() {
        loaderTalonSRX.set(0);
    }
  }