package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class LoaderSubsystem extends SubsystemBase {
    public final WPI_TalonSRX loaderTalonSRX = new WPI_TalonSRX(Constants.LOADER_MOTOR);

    public void loaderUp() {
        loaderTalonSRX.set(1);
    }

    public void loaderDown() {
        loaderTalonSRX.set(-1);
    }
  
    public void stopLoader() {
        loaderTalonSRX.set(0);
    }
  }