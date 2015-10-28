package ca.team4976.sub;

import ca.team4976.io.Input;
import ca.team4976.io.Output;

/**
* @author Marc Levesque
* @version 1.1.1
*/
public class CustomRobotDrive {

    public boolean useDeadBand = false;

    public void arcadeDrive(double steering, double throttle) {

        Output.Motor.DRIVE_LEFT_1.set(throttle + steering);
        Output.Motor.DRIVE_LEFT_2.set(throttle + steering);
        Output.Motor.DRIVE_RIGHT_1.set(-throttle + steering);
        Output.Motor.DRIVE_RIGHT_2.set(-throttle + steering);
    }

    public void directDrive(double left, double right) {

        Output.Motor.DRIVE_LEFT_1.set(left);
        Output.Motor.DRIVE_LEFT_2.set(left);
        Output.Motor.DRIVE_RIGHT_1.set(right);
        Output.Motor.DRIVE_RIGHT_2.set(right);
    }
}
