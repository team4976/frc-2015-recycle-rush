
package ca.team4976.sub;

import ca.team4976.in.Controller;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;

public class Gripper {

    boolean isExtended;

    DigitalInput temp;

    Solenoid solenoid1;
    Solenoid solenoid2;

    /**
     * Initializes the gripper subsystem, called in robotInit();
     *
     * @param nodeID    The Node ID for the PCM on the CAN setup
     * @param port1     The Port ID for the first solenoid on the PCM
     * @param port2     The Port ID for the second solenoid on the PCM
     */
    public Gripper(int nodeID, int port1, int port2) {
        solenoid1 = new Solenoid(11,2);
        solenoid2 = new Solenoid(11,3);

        temp = new DigitalInput(0);
        isExtended = false;
    }

    public void update() {
        if (checkContainer()) {
            if (Controller.Button.START.isDown())
                isExtended = false;
            else if (Controller.Button.X.isDownOnce())
                isExtended = !isExtended;
        } else {
            rotateContainer();
        }

        if (isExtended) {
        } else {
        }

        System.out.println(checkContainer());
        extendSolenoids(isExtended);
    }

    /**
     * Extends the gripper solenoids
     *
     * @param extend    Determines whether or not the solenoids extend
     */
    private void extendSolenoids(boolean extend) {
        solenoid1.set(extend);
        solenoid2.set(extend);
    }

    private boolean checkContainer() {
        return temp.get();
    }

    private void rotateContainer() {
    }

}
