
package ca.team4976.sub;

import ca.team4976.in.Controller;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;

public class Gripper {

    boolean isDown;

    DigitalInput temp;

    Solenoid leftSolenoid;
    Solenoid rightSolenoid;
    Victor leftMotor;
    Victor rightMotor;

    public Gripper() {
        leftSolenoid = new Solenoid(11,2);
        rightSolenoid = new Solenoid(11,3);
        leftMotor = new Victor(2);
        rightMotor = new Victor(3);

        temp = new DigitalInput(0);
        isDown = false;
    }

    public void update() {
        if (checkContainer()) {
            if (Controller.Button.START.isDown())
                isDown = false;
            else if (Controller.Button.X.isDownOnce())
                isDown = !isDown;
        } else {
            rotateContainer();
        }

        if (isDown) {
            leftMotor.set(0.5);
            rightMotor.set(-0.5);
        } else {
            leftMotor.set(0);
            rightMotor.set(0);
        }

        System.out.println(checkContainer());
        setPneumatics();
    }


    private void setPneumatics() {
        leftSolenoid.set(isDown);
        rightSolenoid.set(isDown);
    }

    private boolean checkContainer() {
        return temp.get();
    }

    private void rotateContainer() {
            leftMotor.set(0.5);
            rightMotor.set(0.5);
    }

}
