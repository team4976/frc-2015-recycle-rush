
package ca.team4976.sub;

import edu.wpi.first.wpilibj.SafePWM;
import edu.wpi.first.wpilibj.Solenoid;

public class Gripper {

    boolean isUp;

    Solenoid pneumatic1;
    Solenoid pneumatic2;
    SafePWM motor1;
    SafePWM motor2;

    public Gripper() {
        pneumatic1 = new Solenoid(0);
        pneumatic2 = new Solenoid(1);
        motor1 = new SafePWM(2);
        motor2 = new SafePWM(3);

        isUp = true;
    }

    public void teleopPeriodic() {
        // Check for input, process input.
        if (!checkContainer()){
            if (controller.getRawButton(2 /*SELECT*/)) {
                resetGripper();
            }
            else if(controller.getRawButton(3 /*X*/)) {
                toggleState();
            }
            setPneumatics();
        }
        else {
            if (rotateContainer()){
                // Signal to elevator that container is ready.
            }
        }

        if (!isUp) {
            motor1.setRaw(150);
            motor2.setRaw(104);
        }
        else {
            motor1.setRaw(127);
            motor2.setRaw(127);

        }

    }

    public void testPeriodic() {

    }

    private void toggleState() {
        isUp = !isUp;
    }

    private void setPneumatics() {
        pneumatic1.set(isUp);
        pneumatic2.set(isUp);
    }

    private boolean checkContainer() {
        if (/*Sensor reading is true*/ false) {
            return true;
        }
        return false;
    }

    private boolean rotateContainer() {
        if (/*Handle sensor reads false*/ false) {
            motor1.setRaw(150);
            motor2.setRaw(150);
            return false;
        }
        return true;

    }

    private void resetGripper() {
        isUp = true;
    }

}
