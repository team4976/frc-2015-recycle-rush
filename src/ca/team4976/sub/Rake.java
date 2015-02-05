
package ca.team4976.sub;

import ca.team4976.in.Controller;
import edu.wpi.first.wpilibj.Solenoid;

public class Rake {

    /**
     * Initializes the rake subsystem, called in robotInit();
     *
     * @param nodeID    The Node ID for the PCM on the CAN setup
     * @param port1     The Port ID for the first solenoid on the PCM
     * @param port2     The Port ID for the second solenoid on the PCM
     */
    public Rake(int nodeID, int port1, int port2) {
        isExtended = false;
        solenoid1 = new Solenoid(nodeID, port1);
        solenoid2 = new Solenoid(nodeID, port2);
    }

    //Determines if the solenoid is extended
    public boolean isExtended;

    //The 2 solenoids for the rake
    public Solenoid solenoid1;
    public Solenoid solenoid2;

    /**
     * Called periodically during teleopPeriodic();
     */
    public void update() {
        //If the Start button is down
        if (Controller.Button.START.isDown())
            isExtended = false;

        //If the Y button is down after it has been released (de-bouncing)
        else if (Controller.Button.Y.isDownOnce())
            isExtended = !isExtended;

        //Extend the solenoids based on stored variable
        extendSolenoids(isExtended);
    }

    /**
     * Extends the rake solenoids
     *
     * @param extend    Determines whether or not the solenoids extend
     */
    public void extendSolenoids(boolean extend) {
        solenoid1.set(extend);
        solenoid2.set(extend);
    }

}