
package ca.team4976.sub;

import ca.team4976.in.Controller;
import edu.wpi.first.wpilibj.Solenoid;

public class Rake {

    /**
     * Initializes the rake subsystem, called in robotInit();
     *
     * @param nodeID     The Node ID for the PCM on the CAN setup
     * @param leftMotor  The Port ID for the first solenoid on the PCM
     * @param rightMotor The Port ID for the second solenoid on the PCM
     */
    public Rake(int nodeID, int leftMotor, int rightMotor) {
        isExtended = false;

        leftSolenoid = new Solenoid(nodeID, leftMotor);
        rightSolenoid = new Solenoid(nodeID, rightMotor);
    }

    //Determines if the solenoid is extended
    public boolean isExtended;

    //The 2 solenoids for the rake
    public Solenoid leftSolenoid;
    public Solenoid rightSolenoid;

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
     * @param extend Determines whether or not the solenoids extend
     */
    public void extendSolenoids(boolean extend) {
        leftSolenoid.set(extend);
        rightSolenoid.set(extend);
    }

}