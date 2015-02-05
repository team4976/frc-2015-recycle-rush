package ca.team4976.sub;


import ca.team4976.out.Motors;

public class CustomRobotDrive {

    public void arcadeDrive(double steering, double throttle) {

        Motors.pwmMotors[0].set(throttle + steering);
        Motors.pwmMotors[1].set(throttle + steering);
        Motors.pwmMotors[2].set(-throttle - steering);
        Motors.pwmMotors[3].set(-throttle - steering);
    }
}
