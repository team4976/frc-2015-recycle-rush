
package ca.team4976.sub;

import ca.team4976.in.Controller;
import edu.wpi.first.wpilibj.SafePWM;
import edu.wpi.first.wpilibj.Solenoid;

public class Gripper {

    boolean isDown;

    Solenoid leftSolenoid;
    Solenoid rightSolenoid;
    SafePWM leftMotor;
    SafePWM rightMotor;

    public Gripper() {
        leftSolenoid = new Solenoid(0);
        rightSolenoid = new Solenoid(1);
        leftMotor = new SafePWM(2);
        rightMotor = new SafePWM(3);

        isDown = false;
    }

    public void update() {
        if (!checkContainer()) {
            if (Controller.Button._360_START.isDownOnce())
                isDown = false;
            else if (Controller.Button._360_X.isDownOnce()) {
                isDown = !isDown;
                setPneumatics();
            }
        } else {
            if (rotateContainer()) {

            }
        }

        if (isDown) {
            leftMotor.setRaw(150);
            rightMotor.setRaw(104);
        } else {
            leftMotor.setRaw(127);
            rightMotor.setRaw(127);
        }

    }


    private void setPneumatics() {
        leftSolenoid.set(isDown);
        rightSolenoid.set(isDown);
    }

    private boolean checkContainer() {
        if (/*Sensor reading is true*/ false) {
            return true;
        }
        return false;
    }

    private boolean rotateContainer() {
        if (/*Handle sensor reads false*/ false) {
            leftMotor.setRaw(150);
            rightMotor.setRaw(150);
            return false;
        }
        return true;

    }

}
