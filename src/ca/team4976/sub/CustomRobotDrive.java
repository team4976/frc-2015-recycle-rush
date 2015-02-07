package ca.team4976.sub;

import ca.team4976.out.Output;

public class CustomRobotDrive {

    public void arcadeDrive(double steering, double throttle) {
        Output.Motor.DRIVE_LEFT_1.set(throttle + steering);
        Output.Motor.DRIVE_LEFT_2.set(throttle + steering);
        Output.Motor.DRIVE_RIGHT_1.set(-throttle - steering);
        Output.Motor.DRIVE_RIGHT_2.set(-throttle - steering);
    }
}
