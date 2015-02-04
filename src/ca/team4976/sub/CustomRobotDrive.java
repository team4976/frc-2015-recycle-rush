package ca.team4976.sub;


import ca.team4976.out.AdvancedMotorController;

public class CustomRobotDrive {

    public void arcadeDrive(double steering, double throttle) {

        AdvancedMotorController.motors[0].set(throttle + steering);
        AdvancedMotorController.motors[1].set(throttle + steering);
        AdvancedMotorController.motors[2].set(-throttle - steering);
        AdvancedMotorController.motors[3].set(-throttle - steering);
    }
}
